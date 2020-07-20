package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Naics;
import com.mycompany.myapp.repository.NaicsRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Naics}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class NaicsResource {

    private final Logger log = LoggerFactory.getLogger(NaicsResource.class);

    private static final String ENTITY_NAME = "naics";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NaicsRepository naicsRepository;

    public NaicsResource(NaicsRepository naicsRepository) {
        this.naicsRepository = naicsRepository;
    }

    /**
     * {@code POST  /naics} : Create a new naics.
     *
     * @param naics the naics to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new naics, or with status {@code 400 (Bad Request)} if the naics has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/naics")
    public ResponseEntity<Naics> createNaics(@Valid @RequestBody Naics naics) throws URISyntaxException {
        log.debug("REST request to save Naics : {}", naics);
        if (naics.getId() != null) {
            throw new BadRequestAlertException("A new naics cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Naics result = naicsRepository.save(naics);
        return ResponseEntity.created(new URI("/api/naics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /naics} : Updates an existing naics.
     *
     * @param naics the naics to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated naics,
     * or with status {@code 400 (Bad Request)} if the naics is not valid,
     * or with status {@code 500 (Internal Server Error)} if the naics couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/naics")
    public ResponseEntity<Naics> updateNaics(@Valid @RequestBody Naics naics) throws URISyntaxException {
        log.debug("REST request to update Naics : {}", naics);
        if (naics.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Naics result = naicsRepository.save(naics);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, naics.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /naics} : get all the naics.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of naics in body.
     */
    @GetMapping("/naics")
    public List<Naics> getAllNaics() {
        log.debug("REST request to get all Naics");
        return naicsRepository.findAll();
    }

    /**
     * {@code GET  /naics/:id} : get the "id" naics.
     *
     * @param id the id of the naics to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the naics, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/naics/{id}")
    public ResponseEntity<Naics> getNaics(@PathVariable Long id) {
        log.debug("REST request to get Naics : {}", id);
        Optional<Naics> naics = naicsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(naics);
    }

    /**
     * {@code DELETE  /naics/:id} : delete the "id" naics.
     *
     * @param id the id of the naics to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/naics/{id}")
    public ResponseEntity<Void> deleteNaics(@PathVariable Long id) {
        log.debug("REST request to delete Naics : {}", id);
        naicsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
