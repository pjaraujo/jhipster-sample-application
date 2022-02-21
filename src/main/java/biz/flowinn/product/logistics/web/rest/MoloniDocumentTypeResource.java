package biz.flowinn.product.logistics.web.rest;

import biz.flowinn.product.logistics.repository.MoloniDocumentTypeRepository;
import biz.flowinn.product.logistics.service.MoloniDocumentTypeService;
import biz.flowinn.product.logistics.service.dto.MoloniDocumentTypeDTO;
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
 * REST controller for managing {@link biz.flowinn.product.logistics.domain.MoloniDocumentType}.
 */
@RestController
@RequestMapping("/api")
public class MoloniDocumentTypeResource {

    private final Logger log = LoggerFactory.getLogger(MoloniDocumentTypeResource.class);

    private static final String ENTITY_NAME = "moloniDocumentType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MoloniDocumentTypeService moloniDocumentTypeService;

    private final MoloniDocumentTypeRepository moloniDocumentTypeRepository;

    public MoloniDocumentTypeResource(
        MoloniDocumentTypeService moloniDocumentTypeService,
        MoloniDocumentTypeRepository moloniDocumentTypeRepository
    ) {
        this.moloniDocumentTypeService = moloniDocumentTypeService;
        this.moloniDocumentTypeRepository = moloniDocumentTypeRepository;
    }

    /**
     * {@code POST  /moloni-document-types} : Create a new moloniDocumentType.
     *
     * @param moloniDocumentTypeDTO the moloniDocumentTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new moloniDocumentTypeDTO, or with status {@code 400 (Bad Request)} if the moloniDocumentType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/moloni-document-types")
    public ResponseEntity<MoloniDocumentTypeDTO> createMoloniDocumentType(@RequestBody MoloniDocumentTypeDTO moloniDocumentTypeDTO)
        throws URISyntaxException {
        log.debug("REST request to save MoloniDocumentType : {}", moloniDocumentTypeDTO);
        if (moloniDocumentTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new moloniDocumentType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MoloniDocumentTypeDTO result = moloniDocumentTypeService.save(moloniDocumentTypeDTO);
        return ResponseEntity
            .created(new URI("/api/moloni-document-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /moloni-document-types/:id} : Updates an existing moloniDocumentType.
     *
     * @param id the id of the moloniDocumentTypeDTO to save.
     * @param moloniDocumentTypeDTO the moloniDocumentTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated moloniDocumentTypeDTO,
     * or with status {@code 400 (Bad Request)} if the moloniDocumentTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the moloniDocumentTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/moloni-document-types/{id}")
    public ResponseEntity<MoloniDocumentTypeDTO> updateMoloniDocumentType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MoloniDocumentTypeDTO moloniDocumentTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MoloniDocumentType : {}, {}", id, moloniDocumentTypeDTO);
        if (moloniDocumentTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, moloniDocumentTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!moloniDocumentTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MoloniDocumentTypeDTO result = moloniDocumentTypeService.save(moloniDocumentTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, moloniDocumentTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /moloni-document-types/:id} : Partial updates given fields of an existing moloniDocumentType, field will ignore if it is null
     *
     * @param id the id of the moloniDocumentTypeDTO to save.
     * @param moloniDocumentTypeDTO the moloniDocumentTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated moloniDocumentTypeDTO,
     * or with status {@code 400 (Bad Request)} if the moloniDocumentTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the moloniDocumentTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the moloniDocumentTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/moloni-document-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MoloniDocumentTypeDTO> partialUpdateMoloniDocumentType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MoloniDocumentTypeDTO moloniDocumentTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MoloniDocumentType partially : {}, {}", id, moloniDocumentTypeDTO);
        if (moloniDocumentTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, moloniDocumentTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!moloniDocumentTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MoloniDocumentTypeDTO> result = moloniDocumentTypeService.partialUpdate(moloniDocumentTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, moloniDocumentTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /moloni-document-types} : get all the moloniDocumentTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of moloniDocumentTypes in body.
     */
    @GetMapping("/moloni-document-types")
    public ResponseEntity<List<MoloniDocumentTypeDTO>> getAllMoloniDocumentTypes(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of MoloniDocumentTypes");
        Page<MoloniDocumentTypeDTO> page = moloniDocumentTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /moloni-document-types/:id} : get the "id" moloniDocumentType.
     *
     * @param id the id of the moloniDocumentTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the moloniDocumentTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/moloni-document-types/{id}")
    public ResponseEntity<MoloniDocumentTypeDTO> getMoloniDocumentType(@PathVariable Long id) {
        log.debug("REST request to get MoloniDocumentType : {}", id);
        Optional<MoloniDocumentTypeDTO> moloniDocumentTypeDTO = moloniDocumentTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(moloniDocumentTypeDTO);
    }

    /**
     * {@code DELETE  /moloni-document-types/:id} : delete the "id" moloniDocumentType.
     *
     * @param id the id of the moloniDocumentTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/moloni-document-types/{id}")
    public ResponseEntity<Void> deleteMoloniDocumentType(@PathVariable Long id) {
        log.debug("REST request to delete MoloniDocumentType : {}", id);
        moloniDocumentTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
