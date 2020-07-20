package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SandboxApp;
import com.mycompany.myapp.domain.Naics;
import com.mycompany.myapp.repository.NaicsRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link NaicsResource} REST controller.
 */
@SpringBootTest(classes = SandboxApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class NaicsResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAA";
    private static final String UPDATED_CODE = "BBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    @Autowired
    private NaicsRepository naicsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNaicsMockMvc;

    private Naics naics;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Naics createEntity(EntityManager em) {
        Naics naics = new Naics()
            .description(DEFAULT_DESCRIPTION)
            .code(DEFAULT_CODE)
            .title(DEFAULT_TITLE);
        return naics;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Naics createUpdatedEntity(EntityManager em) {
        Naics naics = new Naics()
            .description(UPDATED_DESCRIPTION)
            .code(UPDATED_CODE)
            .title(UPDATED_TITLE);
        return naics;
    }

    @BeforeEach
    public void initTest() {
        naics = createEntity(em);
    }

    @Test
    @Transactional
    public void createNaics() throws Exception {
        int databaseSizeBeforeCreate = naicsRepository.findAll().size();
        // Create the Naics
        restNaicsMockMvc.perform(post("/api/naics")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(naics)))
            .andExpect(status().isCreated());

        // Validate the Naics in the database
        List<Naics> naicsList = naicsRepository.findAll();
        assertThat(naicsList).hasSize(databaseSizeBeforeCreate + 1);
        Naics testNaics = naicsList.get(naicsList.size() - 1);
        assertThat(testNaics.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testNaics.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testNaics.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    @Transactional
    public void createNaicsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = naicsRepository.findAll().size();

        // Create the Naics with an existing ID
        naics.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNaicsMockMvc.perform(post("/api/naics")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(naics)))
            .andExpect(status().isBadRequest());

        // Validate the Naics in the database
        List<Naics> naicsList = naicsRepository.findAll();
        assertThat(naicsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllNaics() throws Exception {
        // Initialize the database
        naicsRepository.saveAndFlush(naics);

        // Get all the naicsList
        restNaicsMockMvc.perform(get("/api/naics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(naics.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)));
    }
    
    @Test
    @Transactional
    public void getNaics() throws Exception {
        // Initialize the database
        naicsRepository.saveAndFlush(naics);

        // Get the naics
        restNaicsMockMvc.perform(get("/api/naics/{id}", naics.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(naics.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE));
    }
    @Test
    @Transactional
    public void getNonExistingNaics() throws Exception {
        // Get the naics
        restNaicsMockMvc.perform(get("/api/naics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNaics() throws Exception {
        // Initialize the database
        naicsRepository.saveAndFlush(naics);

        int databaseSizeBeforeUpdate = naicsRepository.findAll().size();

        // Update the naics
        Naics updatedNaics = naicsRepository.findById(naics.getId()).get();
        // Disconnect from session so that the updates on updatedNaics are not directly saved in db
        em.detach(updatedNaics);
        updatedNaics
            .description(UPDATED_DESCRIPTION)
            .code(UPDATED_CODE)
            .title(UPDATED_TITLE);

        restNaicsMockMvc.perform(put("/api/naics")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedNaics)))
            .andExpect(status().isOk());

        // Validate the Naics in the database
        List<Naics> naicsList = naicsRepository.findAll();
        assertThat(naicsList).hasSize(databaseSizeBeforeUpdate);
        Naics testNaics = naicsList.get(naicsList.size() - 1);
        assertThat(testNaics.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testNaics.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testNaics.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void updateNonExistingNaics() throws Exception {
        int databaseSizeBeforeUpdate = naicsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNaicsMockMvc.perform(put("/api/naics")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(naics)))
            .andExpect(status().isBadRequest());

        // Validate the Naics in the database
        List<Naics> naicsList = naicsRepository.findAll();
        assertThat(naicsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNaics() throws Exception {
        // Initialize the database
        naicsRepository.saveAndFlush(naics);

        int databaseSizeBeforeDelete = naicsRepository.findAll().size();

        // Delete the naics
        restNaicsMockMvc.perform(delete("/api/naics/{id}", naics.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Naics> naicsList = naicsRepository.findAll();
        assertThat(naicsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
