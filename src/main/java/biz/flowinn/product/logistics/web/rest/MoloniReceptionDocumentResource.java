package biz.flowinn.product.logistics.web.rest;

import biz.flowinn.product.logistics.repository.MoloniReceptionDocumentRepository;
import biz.flowinn.product.logistics.service.MoloniReceptionDocumentService;
import biz.flowinn.product.logistics.service.dto.MoloniReceptionDocumentDTO;
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
 * REST controller for managing {@link biz.flowinn.product.logistics.domain.MoloniReceptionDocument}.
 */
@RestController
@RequestMapping("/api")
public class MoloniReceptionDocumentResource {

    private final Logger log = LoggerFactory.getLogger(MoloniReceptionDocumentResource.class);

    private static final String ENTITY_NAME = "moloniReceptionDocument";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MoloniReceptionDocumentService moloniReceptionDocumentService;

    private final MoloniReceptionDocumentRepository moloniReceptionDocumentRepository;

    public MoloniReceptionDocumentResource(
        MoloniReceptionDocumentService moloniReceptionDocumentService,
        MoloniReceptionDocumentRepository moloniReceptionDocumentRepository
    ) {
        this.moloniReceptionDocumentService = moloniReceptionDocumentService;
        this.moloniReceptionDocumentRepository = moloniReceptionDocumentRepository;
    }

    /**
     * {@code POST  /moloni-reception-documents} : Create a new moloniReceptionDocument.
     *
     * @param moloniReceptionDocumentDTO the moloniReceptionDocumentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new moloniReceptionDocumentDTO, or with status {@code 400 (Bad Request)} if the moloniReceptionDocument has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/moloni-reception-documents")
    public ResponseEntity<MoloniReceptionDocumentDTO> createMoloniReceptionDocument(
        @RequestBody MoloniReceptionDocumentDTO moloniReceptionDocumentDTO
    ) throws URISyntaxException {
        log.debug("REST request to save MoloniReceptionDocument : {}", moloniReceptionDocumentDTO);
        if (moloniReceptionDocumentDTO.getId() != null) {
            throw new BadRequestAlertException("A new moloniReceptionDocument cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MoloniReceptionDocumentDTO result = moloniReceptionDocumentService.save(moloniReceptionDocumentDTO);
        return ResponseEntity
            .created(new URI("/api/moloni-reception-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /moloni-reception-documents/:id} : Updates an existing moloniReceptionDocument.
     *
     * @param id the id of the moloniReceptionDocumentDTO to save.
     * @param moloniReceptionDocumentDTO the moloniReceptionDocumentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated moloniReceptionDocumentDTO,
     * or with status {@code 400 (Bad Request)} if the moloniReceptionDocumentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the moloniReceptionDocumentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/moloni-reception-documents/{id}")
    public ResponseEntity<MoloniReceptionDocumentDTO> updateMoloniReceptionDocument(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MoloniReceptionDocumentDTO moloniReceptionDocumentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MoloniReceptionDocument : {}, {}", id, moloniReceptionDocumentDTO);
        if (moloniReceptionDocumentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, moloniReceptionDocumentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!moloniReceptionDocumentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MoloniReceptionDocumentDTO result = moloniReceptionDocumentService.save(moloniReceptionDocumentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, moloniReceptionDocumentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /moloni-reception-documents/:id} : Partial updates given fields of an existing moloniReceptionDocument, field will ignore if it is null
     *
     * @param id the id of the moloniReceptionDocumentDTO to save.
     * @param moloniReceptionDocumentDTO the moloniReceptionDocumentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated moloniReceptionDocumentDTO,
     * or with status {@code 400 (Bad Request)} if the moloniReceptionDocumentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the moloniReceptionDocumentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the moloniReceptionDocumentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/moloni-reception-documents/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MoloniReceptionDocumentDTO> partialUpdateMoloniReceptionDocument(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MoloniReceptionDocumentDTO moloniReceptionDocumentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MoloniReceptionDocument partially : {}, {}", id, moloniReceptionDocumentDTO);
        if (moloniReceptionDocumentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, moloniReceptionDocumentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!moloniReceptionDocumentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MoloniReceptionDocumentDTO> result = moloniReceptionDocumentService.partialUpdate(moloniReceptionDocumentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, moloniReceptionDocumentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /moloni-reception-documents} : get all the moloniReceptionDocuments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of moloniReceptionDocuments in body.
     */
    @GetMapping("/moloni-reception-documents")
    public ResponseEntity<List<MoloniReceptionDocumentDTO>> getAllMoloniReceptionDocuments(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of MoloniReceptionDocuments");
        Page<MoloniReceptionDocumentDTO> page = moloniReceptionDocumentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /moloni-reception-documents/:id} : get the "id" moloniReceptionDocument.
     *
     * @param id the id of the moloniReceptionDocumentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the moloniReceptionDocumentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/moloni-reception-documents/{id}")
    public ResponseEntity<MoloniReceptionDocumentDTO> getMoloniReceptionDocument(@PathVariable Long id) {
        log.debug("REST request to get MoloniReceptionDocument : {}", id);
        Optional<MoloniReceptionDocumentDTO> moloniReceptionDocumentDTO = moloniReceptionDocumentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(moloniReceptionDocumentDTO);
    }

    /**
     * {@code DELETE  /moloni-reception-documents/:id} : delete the "id" moloniReceptionDocument.
     *
     * @param id the id of the moloniReceptionDocumentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/moloni-reception-documents/{id}")
    public ResponseEntity<Void> deleteMoloniReceptionDocument(@PathVariable Long id) {
        log.debug("REST request to delete MoloniReceptionDocument : {}", id);
        moloniReceptionDocumentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
