package biz.flowinn.product.logistics.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import biz.flowinn.product.logistics.IntegrationTest;
import biz.flowinn.product.logistics.domain.MoloniExpeditionDocument;
import biz.flowinn.product.logistics.domain.enumeration.MoloniEndpoints;
import biz.flowinn.product.logistics.domain.enumeration.MoloniEndpoints;
import biz.flowinn.product.logistics.repository.MoloniExpeditionDocumentRepository;
import biz.flowinn.product.logistics.service.dto.MoloniExpeditionDocumentDTO;
import biz.flowinn.product.logistics.service.mapper.MoloniExpeditionDocumentMapper;
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
 * Integration tests for the {@link MoloniExpeditionDocumentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MoloniExpeditionDocumentResourceIT {

    private static final MoloniEndpoints DEFAULT_ORIGIN = MoloniEndpoints.INVOICES;
    private static final MoloniEndpoints UPDATED_ORIGIN = MoloniEndpoints.RECEIPTS;

    private static final MoloniEndpoints DEFAULT_DESTINATION = MoloniEndpoints.INVOICES;
    private static final MoloniEndpoints UPDATED_DESTINATION = MoloniEndpoints.RECEIPTS;

    private static final String ENTITY_API_URL = "/api/moloni-expedition-documents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MoloniExpeditionDocumentRepository moloniExpeditionDocumentRepository;

    @Autowired
    private MoloniExpeditionDocumentMapper moloniExpeditionDocumentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMoloniExpeditionDocumentMockMvc;

    private MoloniExpeditionDocument moloniExpeditionDocument;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MoloniExpeditionDocument createEntity(EntityManager em) {
        MoloniExpeditionDocument moloniExpeditionDocument = new MoloniExpeditionDocument()
            .origin(DEFAULT_ORIGIN)
            .destination(DEFAULT_DESTINATION);
        return moloniExpeditionDocument;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MoloniExpeditionDocument createUpdatedEntity(EntityManager em) {
        MoloniExpeditionDocument moloniExpeditionDocument = new MoloniExpeditionDocument()
            .origin(UPDATED_ORIGIN)
            .destination(UPDATED_DESTINATION);
        return moloniExpeditionDocument;
    }

    @BeforeEach
    public void initTest() {
        moloniExpeditionDocument = createEntity(em);
    }

    @Test
    @Transactional
    void createMoloniExpeditionDocument() throws Exception {
        int databaseSizeBeforeCreate = moloniExpeditionDocumentRepository.findAll().size();
        // Create the MoloniExpeditionDocument
        MoloniExpeditionDocumentDTO moloniExpeditionDocumentDTO = moloniExpeditionDocumentMapper.toDto(moloniExpeditionDocument);
        restMoloniExpeditionDocumentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(moloniExpeditionDocumentDTO))
            )
            .andExpect(status().isCreated());

        // Validate the MoloniExpeditionDocument in the database
        List<MoloniExpeditionDocument> moloniExpeditionDocumentList = moloniExpeditionDocumentRepository.findAll();
        assertThat(moloniExpeditionDocumentList).hasSize(databaseSizeBeforeCreate + 1);
        MoloniExpeditionDocument testMoloniExpeditionDocument = moloniExpeditionDocumentList.get(moloniExpeditionDocumentList.size() - 1);
        assertThat(testMoloniExpeditionDocument.getOrigin()).isEqualTo(DEFAULT_ORIGIN);
        assertThat(testMoloniExpeditionDocument.getDestination()).isEqualTo(DEFAULT_DESTINATION);
    }

    @Test
    @Transactional
    void createMoloniExpeditionDocumentWithExistingId() throws Exception {
        // Create the MoloniExpeditionDocument with an existing ID
        moloniExpeditionDocument.setId(1L);
        MoloniExpeditionDocumentDTO moloniExpeditionDocumentDTO = moloniExpeditionDocumentMapper.toDto(moloniExpeditionDocument);

        int databaseSizeBeforeCreate = moloniExpeditionDocumentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMoloniExpeditionDocumentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(moloniExpeditionDocumentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MoloniExpeditionDocument in the database
        List<MoloniExpeditionDocument> moloniExpeditionDocumentList = moloniExpeditionDocumentRepository.findAll();
        assertThat(moloniExpeditionDocumentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMoloniExpeditionDocuments() throws Exception {
        // Initialize the database
        moloniExpeditionDocumentRepository.saveAndFlush(moloniExpeditionDocument);

        // Get all the moloniExpeditionDocumentList
        restMoloniExpeditionDocumentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(moloniExpeditionDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].origin").value(hasItem(DEFAULT_ORIGIN.toString())))
            .andExpect(jsonPath("$.[*].destination").value(hasItem(DEFAULT_DESTINATION.toString())));
    }

    @Test
    @Transactional
    void getMoloniExpeditionDocument() throws Exception {
        // Initialize the database
        moloniExpeditionDocumentRepository.saveAndFlush(moloniExpeditionDocument);

        // Get the moloniExpeditionDocument
        restMoloniExpeditionDocumentMockMvc
            .perform(get(ENTITY_API_URL_ID, moloniExpeditionDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(moloniExpeditionDocument.getId().intValue()))
            .andExpect(jsonPath("$.origin").value(DEFAULT_ORIGIN.toString()))
            .andExpect(jsonPath("$.destination").value(DEFAULT_DESTINATION.toString()));
    }

    @Test
    @Transactional
    void getNonExistingMoloniExpeditionDocument() throws Exception {
        // Get the moloniExpeditionDocument
        restMoloniExpeditionDocumentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMoloniExpeditionDocument() throws Exception {
        // Initialize the database
        moloniExpeditionDocumentRepository.saveAndFlush(moloniExpeditionDocument);

        int databaseSizeBeforeUpdate = moloniExpeditionDocumentRepository.findAll().size();

        // Update the moloniExpeditionDocument
        MoloniExpeditionDocument updatedMoloniExpeditionDocument = moloniExpeditionDocumentRepository
            .findById(moloniExpeditionDocument.getId())
            .get();
        // Disconnect from session so that the updates on updatedMoloniExpeditionDocument are not directly saved in db
        em.detach(updatedMoloniExpeditionDocument);
        updatedMoloniExpeditionDocument.origin(UPDATED_ORIGIN).destination(UPDATED_DESTINATION);
        MoloniExpeditionDocumentDTO moloniExpeditionDocumentDTO = moloniExpeditionDocumentMapper.toDto(updatedMoloniExpeditionDocument);

        restMoloniExpeditionDocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, moloniExpeditionDocumentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(moloniExpeditionDocumentDTO))
            )
            .andExpect(status().isOk());

        // Validate the MoloniExpeditionDocument in the database
        List<MoloniExpeditionDocument> moloniExpeditionDocumentList = moloniExpeditionDocumentRepository.findAll();
        assertThat(moloniExpeditionDocumentList).hasSize(databaseSizeBeforeUpdate);
        MoloniExpeditionDocument testMoloniExpeditionDocument = moloniExpeditionDocumentList.get(moloniExpeditionDocumentList.size() - 1);
        assertThat(testMoloniExpeditionDocument.getOrigin()).isEqualTo(UPDATED_ORIGIN);
        assertThat(testMoloniExpeditionDocument.getDestination()).isEqualTo(UPDATED_DESTINATION);
    }

    @Test
    @Transactional
    void putNonExistingMoloniExpeditionDocument() throws Exception {
        int databaseSizeBeforeUpdate = moloniExpeditionDocumentRepository.findAll().size();
        moloniExpeditionDocument.setId(count.incrementAndGet());

        // Create the MoloniExpeditionDocument
        MoloniExpeditionDocumentDTO moloniExpeditionDocumentDTO = moloniExpeditionDocumentMapper.toDto(moloniExpeditionDocument);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMoloniExpeditionDocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, moloniExpeditionDocumentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(moloniExpeditionDocumentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MoloniExpeditionDocument in the database
        List<MoloniExpeditionDocument> moloniExpeditionDocumentList = moloniExpeditionDocumentRepository.findAll();
        assertThat(moloniExpeditionDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMoloniExpeditionDocument() throws Exception {
        int databaseSizeBeforeUpdate = moloniExpeditionDocumentRepository.findAll().size();
        moloniExpeditionDocument.setId(count.incrementAndGet());

        // Create the MoloniExpeditionDocument
        MoloniExpeditionDocumentDTO moloniExpeditionDocumentDTO = moloniExpeditionDocumentMapper.toDto(moloniExpeditionDocument);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMoloniExpeditionDocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(moloniExpeditionDocumentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MoloniExpeditionDocument in the database
        List<MoloniExpeditionDocument> moloniExpeditionDocumentList = moloniExpeditionDocumentRepository.findAll();
        assertThat(moloniExpeditionDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMoloniExpeditionDocument() throws Exception {
        int databaseSizeBeforeUpdate = moloniExpeditionDocumentRepository.findAll().size();
        moloniExpeditionDocument.setId(count.incrementAndGet());

        // Create the MoloniExpeditionDocument
        MoloniExpeditionDocumentDTO moloniExpeditionDocumentDTO = moloniExpeditionDocumentMapper.toDto(moloniExpeditionDocument);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMoloniExpeditionDocumentMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(moloniExpeditionDocumentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MoloniExpeditionDocument in the database
        List<MoloniExpeditionDocument> moloniExpeditionDocumentList = moloniExpeditionDocumentRepository.findAll();
        assertThat(moloniExpeditionDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMoloniExpeditionDocumentWithPatch() throws Exception {
        // Initialize the database
        moloniExpeditionDocumentRepository.saveAndFlush(moloniExpeditionDocument);

        int databaseSizeBeforeUpdate = moloniExpeditionDocumentRepository.findAll().size();

        // Update the moloniExpeditionDocument using partial update
        MoloniExpeditionDocument partialUpdatedMoloniExpeditionDocument = new MoloniExpeditionDocument();
        partialUpdatedMoloniExpeditionDocument.setId(moloniExpeditionDocument.getId());

        restMoloniExpeditionDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMoloniExpeditionDocument.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMoloniExpeditionDocument))
            )
            .andExpect(status().isOk());

        // Validate the MoloniExpeditionDocument in the database
        List<MoloniExpeditionDocument> moloniExpeditionDocumentList = moloniExpeditionDocumentRepository.findAll();
        assertThat(moloniExpeditionDocumentList).hasSize(databaseSizeBeforeUpdate);
        MoloniExpeditionDocument testMoloniExpeditionDocument = moloniExpeditionDocumentList.get(moloniExpeditionDocumentList.size() - 1);
        assertThat(testMoloniExpeditionDocument.getOrigin()).isEqualTo(DEFAULT_ORIGIN);
        assertThat(testMoloniExpeditionDocument.getDestination()).isEqualTo(DEFAULT_DESTINATION);
    }

    @Test
    @Transactional
    void fullUpdateMoloniExpeditionDocumentWithPatch() throws Exception {
        // Initialize the database
        moloniExpeditionDocumentRepository.saveAndFlush(moloniExpeditionDocument);

        int databaseSizeBeforeUpdate = moloniExpeditionDocumentRepository.findAll().size();

        // Update the moloniExpeditionDocument using partial update
        MoloniExpeditionDocument partialUpdatedMoloniExpeditionDocument = new MoloniExpeditionDocument();
        partialUpdatedMoloniExpeditionDocument.setId(moloniExpeditionDocument.getId());

        partialUpdatedMoloniExpeditionDocument.origin(UPDATED_ORIGIN).destination(UPDATED_DESTINATION);

        restMoloniExpeditionDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMoloniExpeditionDocument.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMoloniExpeditionDocument))
            )
            .andExpect(status().isOk());

        // Validate the MoloniExpeditionDocument in the database
        List<MoloniExpeditionDocument> moloniExpeditionDocumentList = moloniExpeditionDocumentRepository.findAll();
        assertThat(moloniExpeditionDocumentList).hasSize(databaseSizeBeforeUpdate);
        MoloniExpeditionDocument testMoloniExpeditionDocument = moloniExpeditionDocumentList.get(moloniExpeditionDocumentList.size() - 1);
        assertThat(testMoloniExpeditionDocument.getOrigin()).isEqualTo(UPDATED_ORIGIN);
        assertThat(testMoloniExpeditionDocument.getDestination()).isEqualTo(UPDATED_DESTINATION);
    }

    @Test
    @Transactional
    void patchNonExistingMoloniExpeditionDocument() throws Exception {
        int databaseSizeBeforeUpdate = moloniExpeditionDocumentRepository.findAll().size();
        moloniExpeditionDocument.setId(count.incrementAndGet());

        // Create the MoloniExpeditionDocument
        MoloniExpeditionDocumentDTO moloniExpeditionDocumentDTO = moloniExpeditionDocumentMapper.toDto(moloniExpeditionDocument);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMoloniExpeditionDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, moloniExpeditionDocumentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(moloniExpeditionDocumentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MoloniExpeditionDocument in the database
        List<MoloniExpeditionDocument> moloniExpeditionDocumentList = moloniExpeditionDocumentRepository.findAll();
        assertThat(moloniExpeditionDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMoloniExpeditionDocument() throws Exception {
        int databaseSizeBeforeUpdate = moloniExpeditionDocumentRepository.findAll().size();
        moloniExpeditionDocument.setId(count.incrementAndGet());

        // Create the MoloniExpeditionDocument
        MoloniExpeditionDocumentDTO moloniExpeditionDocumentDTO = moloniExpeditionDocumentMapper.toDto(moloniExpeditionDocument);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMoloniExpeditionDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(moloniExpeditionDocumentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MoloniExpeditionDocument in the database
        List<MoloniExpeditionDocument> moloniExpeditionDocumentList = moloniExpeditionDocumentRepository.findAll();
        assertThat(moloniExpeditionDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMoloniExpeditionDocument() throws Exception {
        int databaseSizeBeforeUpdate = moloniExpeditionDocumentRepository.findAll().size();
        moloniExpeditionDocument.setId(count.incrementAndGet());

        // Create the MoloniExpeditionDocument
        MoloniExpeditionDocumentDTO moloniExpeditionDocumentDTO = moloniExpeditionDocumentMapper.toDto(moloniExpeditionDocument);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMoloniExpeditionDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(moloniExpeditionDocumentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MoloniExpeditionDocument in the database
        List<MoloniExpeditionDocument> moloniExpeditionDocumentList = moloniExpeditionDocumentRepository.findAll();
        assertThat(moloniExpeditionDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMoloniExpeditionDocument() throws Exception {
        // Initialize the database
        moloniExpeditionDocumentRepository.saveAndFlush(moloniExpeditionDocument);

        int databaseSizeBeforeDelete = moloniExpeditionDocumentRepository.findAll().size();

        // Delete the moloniExpeditionDocument
        restMoloniExpeditionDocumentMockMvc
            .perform(delete(ENTITY_API_URL_ID, moloniExpeditionDocument.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MoloniExpeditionDocument> moloniExpeditionDocumentList = moloniExpeditionDocumentRepository.findAll();
        assertThat(moloniExpeditionDocumentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
