package biz.flowinn.product.logistics.web.rest;

import biz.flowinn.product.logistics.repository.ShippingProcessRepository;
import biz.flowinn.product.logistics.service.ShippingProcessService;
import biz.flowinn.product.logistics.service.dto.ShippingProcessDTO;
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
 * REST controller for managing {@link biz.flowinn.product.logistics.domain.ShippingProcess}.
 */
@RestController
@RequestMapping("/api")
public class ShippingProcessResource {

    private final Logger log = LoggerFactory.getLogger(ShippingProcessResource.class);

    private static final String ENTITY_NAME = "shippingProcess";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShippingProcessService shippingProcessService;

    private final ShippingProcessRepository shippingProcessRepository;

    public ShippingProcessResource(ShippingProcessService shippingProcessService, ShippingProcessRepository shippingProcessRepository) {
        this.shippingProcessService = shippingProcessService;
        this.shippingProcessRepository = shippingProcessRepository;
    }

    /**
     * {@code POST  /shipping-processes} : Create a new shippingProcess.
     *
     * @param shippingProcessDTO the shippingProcessDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shippingProcessDTO, or with status {@code 400 (Bad Request)} if the shippingProcess has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/shipping-processes")
    public ResponseEntity<ShippingProcessDTO> createShippingProcess(@RequestBody ShippingProcessDTO shippingProcessDTO)
        throws URISyntaxException {
        log.debug("REST request to save ShippingProcess : {}", shippingProcessDTO);
        if (shippingProcessDTO.getId() != null) {
            throw new BadRequestAlertException("A new shippingProcess cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShippingProcessDTO result = shippingProcessService.save(shippingProcessDTO);
        return ResponseEntity
            .created(new URI("/api/shipping-processes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /shipping-processes/:id} : Updates an existing shippingProcess.
     *
     * @param id the id of the shippingProcessDTO to save.
     * @param shippingProcessDTO the shippingProcessDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shippingProcessDTO,
     * or with status {@code 400 (Bad Request)} if the shippingProcessDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shippingProcessDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/shipping-processes/{id}")
    public ResponseEntity<ShippingProcessDTO> updateShippingProcess(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ShippingProcessDTO shippingProcessDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ShippingProcess : {}, {}", id, shippingProcessDTO);
        if (shippingProcessDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shippingProcessDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shippingProcessRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ShippingProcessDTO result = shippingProcessService.save(shippingProcessDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shippingProcessDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /shipping-processes/:id} : Partial updates given fields of an existing shippingProcess, field will ignore if it is null
     *
     * @param id the id of the shippingProcessDTO to save.
     * @param shippingProcessDTO the shippingProcessDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shippingProcessDTO,
     * or with status {@code 400 (Bad Request)} if the shippingProcessDTO is not valid,
     * or with status {@code 404 (Not Found)} if the shippingProcessDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the shippingProcessDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/shipping-processes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ShippingProcessDTO> partialUpdateShippingProcess(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ShippingProcessDTO shippingProcessDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ShippingProcess partially : {}, {}", id, shippingProcessDTO);
        if (shippingProcessDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shippingProcessDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shippingProcessRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ShippingProcessDTO> result = shippingProcessService.partialUpdate(shippingProcessDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shippingProcessDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /shipping-processes} : get all the shippingProcesses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shippingProcesses in body.
     */
    @GetMapping("/shipping-processes")
    public ResponseEntity<List<ShippingProcessDTO>> getAllShippingProcesses(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ShippingProcesses");
        Page<ShippingProcessDTO> page = shippingProcessService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /shipping-processes/:id} : get the "id" shippingProcess.
     *
     * @param id the id of the shippingProcessDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shippingProcessDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/shipping-processes/{id}")
    public ResponseEntity<ShippingProcessDTO> getShippingProcess(@PathVariable Long id) {
        log.debug("REST request to get ShippingProcess : {}", id);
        Optional<ShippingProcessDTO> shippingProcessDTO = shippingProcessService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shippingProcessDTO);
    }

    /**
     * {@code DELETE  /shipping-processes/:id} : delete the "id" shippingProcess.
     *
     * @param id the id of the shippingProcessDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/shipping-processes/{id}")
    public ResponseEntity<Void> deleteShippingProcess(@PathVariable Long id) {
        log.debug("REST request to delete ShippingProcess : {}", id);
        shippingProcessService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
