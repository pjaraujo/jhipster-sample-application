package biz.flowinn.product.logistics.web.rest;

import biz.flowinn.product.logistics.repository.MoloniExpeditionDocumentRepository;
import biz.flowinn.product.logistics.service.MoloniExpeditionDocumentService;
import biz.flowinn.product.logistics.service.dto.MoloniExpeditionDocumentDTO;
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
 * REST controller for managing {@link biz.flowinn.product.logistics.domain.MoloniExpeditionDocument}.
 */
@RestController
@RequestMapping("/api")
public class MoloniExpeditionDocumentResource {

    private final Logger log = LoggerFactory.getLogger(MoloniExpeditionDocumentResource.class);

    private static final String ENTITY_NAME = "moloniExpeditionDocument";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MoloniExpeditionDocumentService moloniExpeditionDocumentService;

    private final MoloniExpeditionDocumentRepository moloniExpeditionDocumentRepository;

    public MoloniExpeditionDocumentResource(
        MoloniExpeditionDocumentService moloniExpeditionDocumentService,
        MoloniExpeditionDocumentRepository moloniExpeditionDocumentRepository
    ) {
        this.moloniExpeditionDocumentService = moloniExpeditionDocumentService;
        this.moloniExpeditionDocumentRepository = moloniExpeditionDocumentRepository;
    }

    /**
     * {@code POST  /moloni-expedition-documents} : Create a new moloniExpeditionDocument.
     *
     * @param moloniExpeditionDocumentDTO the moloniExpeditionDocumentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new moloniExpeditionDocumentDTO, or with status {@code 400 (Bad Request)} if the moloniExpeditionDocument has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/moloni-expedition-documents")
    public ResponseEntity<MoloniExpeditionDocumentDTO> createMoloniExpeditionDocument(
        @RequestBody MoloniExpeditionDocumentDTO moloniExpeditionDocumentDTO
    ) throws URISyntaxException {
        log.debug("REST request to save MoloniExpeditionDocument : {}", moloniExpeditionDocumentDTO);
        if (moloniExpeditionDocumentDTO.getId() != null) {
            throw new BadRequestAlertException("A new moloniExpeditionDocument cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MoloniExpeditionDocumentDTO result = moloniExpeditionDocumentService.save(moloniExpeditionDocumentDTO);
        return ResponseEntity
            .created(new URI("/api/moloni-expedition-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /moloni-expedition-documents/:id} : Updates an existing moloniExpeditionDocument.
     *
     * @param id the id of the moloniExpeditionDocumentDTO to save.
     * @param moloniExpeditionDocumentDTO the moloniExpeditionDocumentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated moloniExpeditionDocumentDTO,
     * or with status {@code 400 (Bad Request)} if the moloniExpeditionDocumentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the moloniExpeditionDocumentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/moloni-expedition-documents/{id}")
    public ResponseEntity<MoloniExpeditionDocumentDTO> updateMoloniExpeditionDocument(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MoloniExpeditionDocumentDTO moloniExpeditionDocumentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MoloniExpeditionDocument : {}, {}", id, moloniExpeditionDocumentDTO);
        if (moloniExpeditionDocumentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, moloniExpeditionDocumentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!moloniExpeditionDocumentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MoloniExpeditionDocumentDTO result = moloniExpeditionDocumentService.save(moloniExpeditionDocumentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, moloniExpeditionDocumentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /moloni-expedition-documents/:id} : Partial updates given fields of an existing moloniExpeditionDocument, field will ignore if it is null
     *
     * @param id the id of the moloniExpeditionDocumentDTO to save.
     * @param moloniExpeditionDocumentDTO the moloniExpeditionDocumentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated moloniExpeditionDocumentDTO,
     * or with status {@code 400 (Bad Request)} if the moloniExpeditionDocumentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the moloniExpeditionDocumentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the moloniExpeditionDocumentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/moloni-expedition-documents/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MoloniExpeditionDocumentDTO> partialUpdateMoloniExpeditionDocument(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MoloniExpeditionDocumentDTO moloniExpeditionDocumentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MoloniExpeditionDocument partially : {}, {}", id, moloniExpeditionDocumentDTO);
        if (moloniExpeditionDocumentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, moloniExpeditionDocumentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!moloniExpeditionDocumentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MoloniExpeditionDocumentDTO> result = moloniExpeditionDocumentService.partialUpdate(moloniExpeditionDocumentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, moloniExpeditionDocumentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /moloni-expedition-documents} : get all the moloniExpeditionDocuments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of moloniExpeditionDocuments in body.
     */
    @GetMapping("/moloni-expedition-documents")
    public ResponseEntity<List<MoloniExpeditionDocumentDTO>> getAllMoloniExpeditionDocuments(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of MoloniExpeditionDocuments");
        Page<MoloniExpeditionDocumentDTO> page = moloniExpeditionDocumentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /moloni-expedition-documents/:id} : get the "id" moloniExpeditionDocument.
     *
     * @param id the id of the moloniExpeditionDocumentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the moloniExpeditionDocumentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/moloni-expedition-documents/{id}")
    public ResponseEntity<MoloniExpeditionDocumentDTO> getMoloniExpeditionDocument(@PathVariable Long id) {
        log.debug("REST request to get MoloniExpeditionDocument : {}", id);
        Optional<MoloniExpeditionDocumentDTO> moloniExpeditionDocumentDTO = moloniExpeditionDocumentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(moloniExpeditionDocumentDTO);
    }

    /**
     * {@code DELETE  /moloni-expedition-documents/:id} : delete the "id" moloniExpeditionDocument.
     *
     * @param id the id of the moloniExpeditionDocumentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/moloni-expedition-documents/{id}")
    public ResponseEntity<Void> deleteMoloniExpeditionDocument(@PathVariable Long id) {
        log.debug("REST request to delete MoloniExpeditionDocument : {}", id);
        moloniExpeditionDocumentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
