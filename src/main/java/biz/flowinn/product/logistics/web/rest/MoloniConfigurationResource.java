package biz.flowinn.product.logistics.web.rest;

import biz.flowinn.product.logistics.repository.MoloniConfigurationRepository;
import biz.flowinn.product.logistics.service.MoloniConfigurationService;
import biz.flowinn.product.logistics.service.dto.MoloniConfigurationDTO;
import biz.flowinn.product.logistics.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link biz.flowinn.product.logistics.domain.MoloniConfiguration}.
 */
@RestController
@RequestMapping("/api")
public class MoloniConfigurationResource {

    private final Logger log = LoggerFactory.getLogger(MoloniConfigurationResource.class);

    private static final String ENTITY_NAME = "moloniConfiguration";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MoloniConfigurationService moloniConfigurationService;

    private final MoloniConfigurationRepository moloniConfigurationRepository;

    public MoloniConfigurationResource(
        MoloniConfigurationService moloniConfigurationService,
        MoloniConfigurationRepository moloniConfigurationRepository
    ) {
        this.moloniConfigurationService = moloniConfigurationService;
        this.moloniConfigurationRepository = moloniConfigurationRepository;
    }

    /**
     * {@code POST  /moloni-configurations} : Create a new moloniConfiguration.
     *
     * @param moloniConfigurationDTO the moloniConfigurationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new moloniConfigurationDTO, or with status {@code 400 (Bad Request)} if the moloniConfiguration has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/moloni-configurations")
    public ResponseEntity<MoloniConfigurationDTO> createMoloniConfiguration(@RequestBody MoloniConfigurationDTO moloniConfigurationDTO)
        throws URISyntaxException {
        log.debug("REST request to save MoloniConfiguration : {}", moloniConfigurationDTO);
        if (moloniConfigurationDTO.getId() != null) {
            throw new BadRequestAlertException("A new moloniConfiguration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MoloniConfigurationDTO result = moloniConfigurationService.save(moloniConfigurationDTO);
        return ResponseEntity
            .created(new URI("/api/moloni-configurations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /moloni-configurations/:id} : Updates an existing moloniConfiguration.
     *
     * @param id the id of the moloniConfigurationDTO to save.
     * @param moloniConfigurationDTO the moloniConfigurationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated moloniConfigurationDTO,
     * or with status {@code 400 (Bad Request)} if the moloniConfigurationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the moloniConfigurationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/moloni-configurations/{id}")
    public ResponseEntity<MoloniConfigurationDTO> updateMoloniConfiguration(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MoloniConfigurationDTO moloniConfigurationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MoloniConfiguration : {}, {}", id, moloniConfigurationDTO);
        if (moloniConfigurationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, moloniConfigurationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!moloniConfigurationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MoloniConfigurationDTO result = moloniConfigurationService.save(moloniConfigurationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, moloniConfigurationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /moloni-configurations/:id} : Partial updates given fields of an existing moloniConfiguration, field will ignore if it is null
     *
     * @param id the id of the moloniConfigurationDTO to save.
     * @param moloniConfigurationDTO the moloniConfigurationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated moloniConfigurationDTO,
     * or with status {@code 400 (Bad Request)} if the moloniConfigurationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the moloniConfigurationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the moloniConfigurationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/moloni-configurations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MoloniConfigurationDTO> partialUpdateMoloniConfiguration(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MoloniConfigurationDTO moloniConfigurationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MoloniConfiguration partially : {}, {}", id, moloniConfigurationDTO);
        if (moloniConfigurationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, moloniConfigurationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!moloniConfigurationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MoloniConfigurationDTO> result = moloniConfigurationService.partialUpdate(moloniConfigurationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, moloniConfigurationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /moloni-configurations} : get all the moloniConfigurations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of moloniConfigurations in body.
     */
    @GetMapping("/moloni-configurations")
    public ResponseEntity<List<MoloniConfigurationDTO>> getAllMoloniConfigurations(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of MoloniConfigurations");
        Page<MoloniConfigurationDTO> page = moloniConfigurationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /moloni-configurations/:id} : get the "id" moloniConfiguration.
     *
     * @param id the id of the moloniConfigurationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the moloniConfigurationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/moloni-configurations/{id}")
    public ResponseEntity<MoloniConfigurationDTO> getMoloniConfiguration(@PathVariable Long id) {
        log.debug("REST request to get MoloniConfiguration : {}", id);
        Optional<MoloniConfigurationDTO> moloniConfigurationDTO = moloniConfigurationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(moloniConfigurationDTO);
    }

    /**
     * {@code DELETE  /moloni-configurations/:id} : delete the "id" moloniConfiguration.
     *
     * @param id the id of the moloniConfigurationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/moloni-configurations/{id}")
    public ResponseEntity<Void> deleteMoloniConfiguration(@PathVariable Long id) {
        log.debug("REST request to delete MoloniConfiguration : {}", id);
        moloniConfigurationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
