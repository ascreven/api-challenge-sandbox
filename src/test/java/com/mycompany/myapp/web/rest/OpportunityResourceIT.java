package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SandboxApp;
import com.mycompany.myapp.domain.Opportunity;
import com.mycompany.myapp.repository.OpportunityRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link OpportunityResource} REST controller.
 */
@SpringBootTest(classes = SandboxApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class OpportunityResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_SOL_NUM = "AAAAAAAAAA";
    private static final String UPDATED_SOL_NUM = "BBBBBBBBBB";

    private static final String DEFAULT_NOTICEID = "AAAAAAAAAA";
    private static final String UPDATED_NOTICEID = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_ZIP = "AAAAAAAAAA";
    private static final String UPDATED_ZIP = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_POSTED_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_POSTED_FROM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_POSTED_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_POSTED_TO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_REPONSE_DEAD_LINE = "AAAAAAAAAA";
    private static final String UPDATED_REPONSE_DEAD_LINE = "BBBBBBBBBB";

    private static final String DEFAULT_CLASSIFICATION_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CLASSIFICATION_CODE = "BBBBBBBBBB";

    @Autowired
    private OpportunityRepository opportunityRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOpportunityMockMvc;

    private Opportunity opportunity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Opportunity createEntity(EntityManager em) {
        Opportunity opportunity = new Opportunity()
            .title(DEFAULT_TITLE)
            .solNum(DEFAULT_SOL_NUM)
            .noticeid(DEFAULT_NOTICEID)
            .state(DEFAULT_STATE)
            .zip(DEFAULT_ZIP)
            .postedFrom(DEFAULT_POSTED_FROM)
            .postedTo(DEFAULT_POSTED_TO)
            .reponseDeadLine(DEFAULT_REPONSE_DEAD_LINE)
            .classificationCode(DEFAULT_CLASSIFICATION_CODE);
        return opportunity;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Opportunity createUpdatedEntity(EntityManager em) {
        Opportunity opportunity = new Opportunity()
            .title(UPDATED_TITLE)
            .solNum(UPDATED_SOL_NUM)
            .noticeid(UPDATED_NOTICEID)
            .state(UPDATED_STATE)
            .zip(UPDATED_ZIP)
            .postedFrom(UPDATED_POSTED_FROM)
            .postedTo(UPDATED_POSTED_TO)
            .reponseDeadLine(UPDATED_REPONSE_DEAD_LINE)
            .classificationCode(UPDATED_CLASSIFICATION_CODE);
        return opportunity;
    }

    @BeforeEach
    public void initTest() {
        opportunity = createEntity(em);
    }

    @Test
    @Transactional
    public void createOpportunity() throws Exception {
        int databaseSizeBeforeCreate = opportunityRepository.findAll().size();
        // Create the Opportunity
        restOpportunityMockMvc.perform(post("/api/opportunities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(opportunity)))
            .andExpect(status().isCreated());

        // Validate the Opportunity in the database
        List<Opportunity> opportunityList = opportunityRepository.findAll();
        assertThat(opportunityList).hasSize(databaseSizeBeforeCreate + 1);
        Opportunity testOpportunity = opportunityList.get(opportunityList.size() - 1);
        assertThat(testOpportunity.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testOpportunity.getSolNum()).isEqualTo(DEFAULT_SOL_NUM);
        assertThat(testOpportunity.getNoticeid()).isEqualTo(DEFAULT_NOTICEID);
        assertThat(testOpportunity.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testOpportunity.getZip()).isEqualTo(DEFAULT_ZIP);
        assertThat(testOpportunity.getPostedFrom()).isEqualTo(DEFAULT_POSTED_FROM);
        assertThat(testOpportunity.getPostedTo()).isEqualTo(DEFAULT_POSTED_TO);
        assertThat(testOpportunity.getReponseDeadLine()).isEqualTo(DEFAULT_REPONSE_DEAD_LINE);
        assertThat(testOpportunity.getClassificationCode()).isEqualTo(DEFAULT_CLASSIFICATION_CODE);
    }

    @Test
    @Transactional
    public void createOpportunityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = opportunityRepository.findAll().size();

        // Create the Opportunity with an existing ID
        opportunity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOpportunityMockMvc.perform(post("/api/opportunities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(opportunity)))
            .andExpect(status().isBadRequest());

        // Validate the Opportunity in the database
        List<Opportunity> opportunityList = opportunityRepository.findAll();
        assertThat(opportunityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOpportunities() throws Exception {
        // Initialize the database
        opportunityRepository.saveAndFlush(opportunity);

        // Get all the opportunityList
        restOpportunityMockMvc.perform(get("/api/opportunities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(opportunity.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].solNum").value(hasItem(DEFAULT_SOL_NUM)))
            .andExpect(jsonPath("$.[*].noticeid").value(hasItem(DEFAULT_NOTICEID)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].zip").value(hasItem(DEFAULT_ZIP)))
            .andExpect(jsonPath("$.[*].postedFrom").value(hasItem(DEFAULT_POSTED_FROM.toString())))
            .andExpect(jsonPath("$.[*].postedTo").value(hasItem(DEFAULT_POSTED_TO.toString())))
            .andExpect(jsonPath("$.[*].reponseDeadLine").value(hasItem(DEFAULT_REPONSE_DEAD_LINE)))
            .andExpect(jsonPath("$.[*].classificationCode").value(hasItem(DEFAULT_CLASSIFICATION_CODE)));
    }
    
    @Test
    @Transactional
    public void getOpportunity() throws Exception {
        // Initialize the database
        opportunityRepository.saveAndFlush(opportunity);

        // Get the opportunity
        restOpportunityMockMvc.perform(get("/api/opportunities/{id}", opportunity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(opportunity.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.solNum").value(DEFAULT_SOL_NUM))
            .andExpect(jsonPath("$.noticeid").value(DEFAULT_NOTICEID))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
            .andExpect(jsonPath("$.zip").value(DEFAULT_ZIP))
            .andExpect(jsonPath("$.postedFrom").value(DEFAULT_POSTED_FROM.toString()))
            .andExpect(jsonPath("$.postedTo").value(DEFAULT_POSTED_TO.toString()))
            .andExpect(jsonPath("$.reponseDeadLine").value(DEFAULT_REPONSE_DEAD_LINE))
            .andExpect(jsonPath("$.classificationCode").value(DEFAULT_CLASSIFICATION_CODE));
    }
    @Test
    @Transactional
    public void getNonExistingOpportunity() throws Exception {
        // Get the opportunity
        restOpportunityMockMvc.perform(get("/api/opportunities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOpportunity() throws Exception {
        // Initialize the database
        opportunityRepository.saveAndFlush(opportunity);

        int databaseSizeBeforeUpdate = opportunityRepository.findAll().size();

        // Update the opportunity
        Opportunity updatedOpportunity = opportunityRepository.findById(opportunity.getId()).get();
        // Disconnect from session so that the updates on updatedOpportunity are not directly saved in db
        em.detach(updatedOpportunity);
        updatedOpportunity
            .title(UPDATED_TITLE)
            .solNum(UPDATED_SOL_NUM)
            .noticeid(UPDATED_NOTICEID)
            .state(UPDATED_STATE)
            .zip(UPDATED_ZIP)
            .postedFrom(UPDATED_POSTED_FROM)
            .postedTo(UPDATED_POSTED_TO)
            .reponseDeadLine(UPDATED_REPONSE_DEAD_LINE)
            .classificationCode(UPDATED_CLASSIFICATION_CODE);

        restOpportunityMockMvc.perform(put("/api/opportunities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedOpportunity)))
            .andExpect(status().isOk());

        // Validate the Opportunity in the database
        List<Opportunity> opportunityList = opportunityRepository.findAll();
        assertThat(opportunityList).hasSize(databaseSizeBeforeUpdate);
        Opportunity testOpportunity = opportunityList.get(opportunityList.size() - 1);
        assertThat(testOpportunity.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testOpportunity.getSolNum()).isEqualTo(UPDATED_SOL_NUM);
        assertThat(testOpportunity.getNoticeid()).isEqualTo(UPDATED_NOTICEID);
        assertThat(testOpportunity.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testOpportunity.getZip()).isEqualTo(UPDATED_ZIP);
        assertThat(testOpportunity.getPostedFrom()).isEqualTo(UPDATED_POSTED_FROM);
        assertThat(testOpportunity.getPostedTo()).isEqualTo(UPDATED_POSTED_TO);
        assertThat(testOpportunity.getReponseDeadLine()).isEqualTo(UPDATED_REPONSE_DEAD_LINE);
        assertThat(testOpportunity.getClassificationCode()).isEqualTo(UPDATED_CLASSIFICATION_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingOpportunity() throws Exception {
        int databaseSizeBeforeUpdate = opportunityRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOpportunityMockMvc.perform(put("/api/opportunities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(opportunity)))
            .andExpect(status().isBadRequest());

        // Validate the Opportunity in the database
        List<Opportunity> opportunityList = opportunityRepository.findAll();
        assertThat(opportunityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOpportunity() throws Exception {
        // Initialize the database
        opportunityRepository.saveAndFlush(opportunity);

        int databaseSizeBeforeDelete = opportunityRepository.findAll().size();

        // Delete the opportunity
        restOpportunityMockMvc.perform(delete("/api/opportunities/{id}", opportunity.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Opportunity> opportunityList = opportunityRepository.findAll();
        assertThat(opportunityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
