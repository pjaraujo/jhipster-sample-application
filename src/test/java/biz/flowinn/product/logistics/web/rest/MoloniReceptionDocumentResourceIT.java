package biz.flowinn.product.logistics.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import biz.flowinn.product.logistics.IntegrationTest;
import biz.flowinn.product.logistics.domain.MoloniReceptionDocument;
import biz.flowinn.product.logistics.domain.enumeration.DocumentType;
import biz.flowinn.product.logistics.domain.enumeration.MoloniEndpoints;
import biz.flowinn.product.logistics.domain.enumeration.MoloniEndpoints;
import biz.flowinn.product.logistics.repository.MoloniReceptionDocumentRepository;
import biz.flowinn.product.logistics.service.dto.MoloniReceptionDocumentDTO;
import biz.flowinn.product.logistics.service.mapper.MoloniReceptionDocumentMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MoloniReceptionDocumentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MoloniReceptionDocumentResourceIT {

    private static final MoloniEndpoints DEFAULT_ORIGIN = MoloniEndpoints.INVOICES;
    private static final MoloniEndpoints UPDATED_ORIGIN = MoloniEndpoints.RECEIPTS;

    private static final MoloniEndpoints DEFAULT_DESTINATION = MoloniEndpoints.INVOICES;
    private static final MoloniEndpoints UPDATED_DESTINATION = MoloniEndpoints.RECEIPTS;

    private static final DocumentType DEFAULT_DOCUMENT_TYPE = DocumentType.WAREHOUSE_TRANSFER;
    private static final DocumentType UPDATED_DOCUMENT_TYPE = DocumentType.PURCHASE_ORDER;

    private static final String ENTITY_API_URL = "/api/moloni-reception-documents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MoloniReceptionDocumentRepository moloniReceptionDocumentRepository;

    @Autowired
    private MoloniReceptionDocumentMapper moloniReceptionDocumentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMoloniReceptionDocumentMockMvc;

    private MoloniReceptionDocument moloniReceptionDocument;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MoloniReceptionDocument createEntity(EntityManager em) {
        MoloniReceptionDocument moloniReceptionDocument = new MoloniReceptionDocument()
            .origin(DEFAULT_ORIGIN)
            .destination(DEFAULT_DESTINATION)
            .documentType(DEFAULT_DOCUMENT_TYPE);
        return moloniReceptionDocument;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MoloniReceptionDocument createUpdatedEntity(EntityManager em) {
        MoloniReceptionDocument moloniReceptionDocument = new MoloniReceptionDocument()
            .origin(UPDATED_ORIGIN)
            .destination(UPDATED_DESTINATION)
            .documentType(UPDATED_DOCUMENT_TYPE);
        return moloniReceptionDocument;
    }

    @BeforeEach
    public void initTest() {
        moloniReceptionDocument = createEntity(em);
    }

    @Test
    @Transactional
    void createMoloniReceptionDocument() throws Exception {
        int databaseSizeBeforeCreate = moloniReceptionDocumentRepository.findAll().size();
        // Create the MoloniReceptionDocument
        MoloniReceptionDocumentDTO moloniReceptionDocumentDTO = moloniReceptionDocumentMapper.toDto(moloniReceptionDocument);
        restMoloniReceptionDocumentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(moloniReceptionDocumentDTO))
            )
            .andExpect(status().isCreated());

        // Validate the MoloniReceptionDocument in the database
        List<MoloniReceptionDocument> moloniReceptionDocumentList = moloniReceptionDocumentRepository.findAll();
        assertThat(moloniReceptionDocumentList).hasSize(databaseSizeBeforeCreate + 1);
        MoloniReceptionDocument testMoloniReceptionDocument = moloniReceptionDocumentList.get(moloniReceptionDocumentList.size() - 1);
        assertThat(testMoloniReceptionDocument.getOrigin()).isEqualTo(DEFAULT_ORIGIN);
        assertThat(testMoloniReceptionDocument.getDestination()).isEqualTo(DEFAULT_DESTINATION);
        assertThat(testMoloniReceptionDocument.getDocumentType()).isEqualTo(DEFAULT_DOCUMENT_TYPE);
    }

    @Test
    @Transactional
    void createMoloniReceptionDocumentWithExistingId() throws Exception {
        // Create the MoloniReceptionDocument with an existing ID
        moloniReceptionDocument.setId(1L);
        MoloniReceptionDocumentDTO moloniReceptionDocumentDTO = moloniReceptionDocumentMapper.toDto(moloniReceptionDocument);

        int databaseSizeBeforeCreate = moloniReceptionDocumentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMoloniReceptionDocumentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(moloniReceptionDocumentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MoloniReceptionDocument in the database
        List<MoloniReceptionDocument> moloniReceptionDocumentList = moloniReceptionDocumentRepository.findAll();
        assertThat(moloniReceptionDocumentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMoloniReceptionDocuments() throws Exception {
        // Initialize the database
        moloniReceptionDocumentRepository.saveAndFlush(moloniReceptionDocument);

        // Get all the moloniReceptionDocumentList
        restMoloniReceptionDocumentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(moloniReceptionDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].origin").value(hasItem(DEFAULT_ORIGIN.toString())))
            .andExpect(jsonPath("$.[*].destination").value(hasItem(DEFAULT_DESTINATION.toString())))
            .andExpect(jsonPath("$.[*].documentType").value(hasItem(DEFAULT_DOCUMENT_TYPE.toString())));
    }

    @Test
    @Transactional
    void getMoloniReceptionDocument() throws Exception {
        // Initialize the database
        moloniReceptionDocumentRepository.saveAndFlush(moloniReceptionDocument);

        // Get the moloniReceptionDocument
        restMoloniReceptionDocumentMockMvc
            .perform(get(ENTITY_API_URL_ID, moloniReceptionDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(moloniReceptionDocument.getId().intValue()))
            .andExpect(jsonPath("$.origin").value(DEFAULT_ORIGIN.toString()))
            .andExpect(jsonPath("$.destination").value(DEFAULT_DESTINATION.toString()))
            .andExpect(jsonPath("$.documentType").value(DEFAULT_DOCUMENT_TYPE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingMoloniReceptionDocument() throws Exception {
        // Get the moloniReceptionDocument
        restMoloniReceptionDocumentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMoloniReceptionDocument() throws Exception {
        // Initialize the database
        moloniReceptionDocumentRepository.saveAndFlush(moloniReceptionDocument);

        int databaseSizeBeforeUpdate = moloniReceptionDocumentRepository.findAll().size();

        // Update the moloniReceptionDocument
        MoloniReceptionDocument updatedMoloniReceptionDocument = moloniReceptionDocumentRepository
            .findById(moloniReceptionDocument.getId())
            .get();
        // Disconnect from session so that the updates on updatedMoloniReceptionDocument are not directly saved in db
        em.detach(updatedMoloniReceptionDocument);
        updatedMoloniReceptionDocument.origin(UPDATED_ORIGIN).destination(UPDATED_DESTINATION).documentType(UPDATED_DOCUMENT_TYPE);
        MoloniReceptionDocumentDTO moloniReceptionDocumentDTO = moloniReceptionDocumentMapper.toDto(updatedMoloniReceptionDocument);

        restMoloniReceptionDocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, moloniReceptionDocumentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(moloniReceptionDocumentDTO))
            )
            .andExpect(status().isOk());

        // Validate the MoloniReceptionDocument in the database
        List<MoloniReceptionDocument> moloniReceptionDocumentList = moloniReceptionDocumentRepository.findAll();
        assertThat(moloniReceptionDocumentList).hasSize(databaseSizeBeforeUpdate);
        MoloniReceptionDocument testMoloniReceptionDocument = moloniReceptionDocumentList.get(moloniReceptionDocumentList.size() - 1);
        assertThat(testMoloniReceptionDocument.getOrigin()).isEqualTo(UPDATED_ORIGIN);
        assertThat(testMoloniReceptionDocument.getDestination()).isEqualTo(UPDATED_DESTINATION);
        assertThat(testMoloniReceptionDocument.getDocumentType()).isEqualTo(UPDATED_DOCUMENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingMoloniReceptionDocument() throws Exception {
        int databaseSizeBeforeUpdate = moloniReceptionDocumentRepository.findAll().size();
        moloniReceptionDocument.setId(count.incrementAndGet());

        // Create the MoloniReceptionDocument
        MoloniReceptionDocumentDTO moloniReceptionDocumentDTO = moloniReceptionDocumentMapper.toDto(moloniReceptionDocument);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMoloniReceptionDocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, moloniReceptionDocumentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(moloniReceptionDocumentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MoloniReceptionDocument in the database
        List<MoloniReceptionDocument> moloniReceptionDocumentList = moloniReceptionDocumentRepository.findAll();
        assertThat(moloniReceptionDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMoloniReceptionDocument() throws Exception {
        int databaseSizeBeforeUpdate = moloniReceptionDocumentRepository.findAll().size();
        moloniReceptionDocument.setId(count.incrementAndGet());

        // Create the MoloniReceptionDocument
        MoloniReceptionDocumentDTO moloniReceptionDocumentDTO = moloniReceptionDocumentMapper.toDto(moloniReceptionDocument);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMoloniReceptionDocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(moloniReceptionDocumentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MoloniReceptionDocument in the database
        List<MoloniReceptionDocument> moloniReceptionDocumentList = moloniReceptionDocumentRepository.findAll();
        assertThat(moloniReceptionDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMoloniReceptionDocument() throws Exception {
        int databaseSizeBeforeUpdate = moloniReceptionDocumentRepository.findAll().size();
        moloniReceptionDocument.setId(count.incrementAndGet());

        // Create the MoloniReceptionDocument
        MoloniReceptionDocumentDTO moloniReceptionDocumentDTO = moloniReceptionDocumentMapper.toDto(moloniReceptionDocument);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMoloniReceptionDocumentMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(moloniReceptionDocumentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MoloniReceptionDocument in the database
        List<MoloniReceptionDocument> moloniReceptionDocumentList = moloniReceptionDocumentRepository.findAll();
        assertThat(moloniReceptionDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMoloniReceptionDocumentWithPatch() throws Exception {
        // Initialize the database
        moloniReceptionDocumentRepository.saveAndFlush(moloniReceptionDocument);

        int databaseSizeBeforeUpdate = moloniReceptionDocumentRepository.findAll().size();

        // Update the moloniReceptionDocument using partial update
        MoloniReceptionDocument partialUpdatedMoloniReceptionDocument = new MoloniReceptionDocument();
        partialUpdatedMoloniReceptionDocument.setId(moloniReceptionDocument.getId());

        partialUpdatedMoloniReceptionDocument.documentType(UPDATED_DOCUMENT_TYPE);

        restMoloniReceptionDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMoloniReceptionDocument.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMoloniReceptionDocument))
            )
            .andExpect(status().isOk());

        // Validate the MoloniReceptionDocument in the database
        List<MoloniReceptionDocument> moloniReceptionDocumentList = moloniReceptionDocumentRepository.findAll();
        assertThat(moloniReceptionDocumentList).hasSize(databaseSizeBeforeUpdate);
        MoloniReceptionDocument testMoloniReceptionDocument = moloniReceptionDocumentList.get(moloniReceptionDocumentList.size() - 1);
        assertThat(testMoloniReceptionDocument.getOrigin()).isEqualTo(DEFAULT_ORIGIN);
        assertThat(testMoloniReceptionDocument.getDestination()).isEqualTo(DEFAULT_DESTINATION);
        assertThat(testMoloniReceptionDocument.getDocumentType()).isEqualTo(UPDATED_DOCUMENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateMoloniReceptionDocumentWithPatch() throws Exception {
        // Initialize the database
        moloniReceptionDocumentRepository.saveAndFlush(moloniReceptionDocument);

        int databaseSizeBeforeUpdate = moloniReceptionDocumentRepository.findAll().size();

        // Update the moloniReceptionDocument using partial update
        MoloniReceptionDocument partialUpdatedMoloniReceptionDocument = new MoloniReceptionDocument();
        partialUpdatedMoloniReceptionDocument.setId(moloniReceptionDocument.getId());

        partialUpdatedMoloniReceptionDocument.origin(UPDATED_ORIGIN).destination(UPDATED_DESTINATION).documentType(UPDATED_DOCUMENT_TYPE);

        restMoloniReceptionDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMoloniReceptionDocument.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMoloniReceptionDocument))
            )
            .andExpect(status().isOk());

        // Validate the MoloniReceptionDocument in the database
        List<MoloniReceptionDocument> moloniReceptionDocumentList = moloniReceptionDocumentRepository.findAll();
        assertThat(moloniReceptionDocumentList).hasSize(databaseSizeBeforeUpdate);
        MoloniReceptionDocument testMoloniReceptionDocument = moloniReceptionDocumentList.get(moloniReceptionDocumentList.size() - 1);
        assertThat(testMoloniReceptionDocument.getOrigin()).isEqualTo(UPDATED_ORIGIN);
        assertThat(testMoloniReceptionDocument.getDestination()).isEqualTo(UPDATED_DESTINATION);
        assertThat(testMoloniReceptionDocument.getDocumentType()).isEqualTo(UPDATED_DOCUMENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingMoloniReceptionDocument() throws Exception {
        int databaseSizeBeforeUpdate = moloniReceptionDocumentRepository.findAll().size();
        moloniReceptionDocument.setId(count.incrementAndGet());

        // Create the MoloniReceptionDocument
        MoloniReceptionDocumentDTO moloniReceptionDocumentDTO = moloniReceptionDocumentMapper.toDto(moloniReceptionDocument);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMoloniReceptionDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, moloniReceptionDocumentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(moloniReceptionDocumentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MoloniReceptionDocument in the database
        List<MoloniReceptionDocument> moloniReceptionDocumentList = moloniReceptionDocumentRepository.findAll();
        assertThat(moloniReceptionDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMoloniReceptionDocument() throws Exception {
        int databaseSizeBeforeUpdate = moloniReceptionDocumentRepository.findAll().size();
        moloniReceptionDocument.setId(count.incrementAndGet());

        // Create the MoloniReceptionDocument
        MoloniReceptionDocumentDTO moloniReceptionDocumentDTO = moloniReceptionDocumentMapper.toDto(moloniReceptionDocument);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMoloniReceptionDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(moloniReceptionDocumentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MoloniReceptionDocument in the database
        List<MoloniReceptionDocument> moloniReceptionDocumentList = moloniReceptionDocumentRepository.findAll();
        assertThat(moloniReceptionDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMoloniReceptionDocument() throws Exception {
        int databaseSizeBeforeUpdate = moloniReceptionDocumentRepository.findAll().size();
        moloniReceptionDocument.setId(count.incrementAndGet());

        // Create the MoloniReceptionDocument
        MoloniReceptionDocumentDTO moloniReceptionDocumentDTO = moloniReceptionDocumentMapper.toDto(moloniReceptionDocument);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMoloniReceptionDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(moloniReceptionDocumentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MoloniReceptionDocument in the database
        List<MoloniReceptionDocument> moloniReceptionDocumentList = moloniReceptionDocumentRepository.findAll();
        assertThat(moloniReceptionDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMoloniReceptionDocument() throws Exception {
        // Initialize the database
        moloniReceptionDocumentRepository.saveAndFlush(moloniReceptionDocument);

        int databaseSizeBeforeDelete = moloniReceptionDocumentRepository.findAll().size();

        // Delete the moloniReceptionDocument
        restMoloniReceptionDocumentMockMvc
            .perform(delete(ENTITY_API_URL_ID, moloniReceptionDocument.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MoloniReceptionDocument> moloniReceptionDocumentList = moloniReceptionDocumentRepository.findAll();
        assertThat(moloniReceptionDocumentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
