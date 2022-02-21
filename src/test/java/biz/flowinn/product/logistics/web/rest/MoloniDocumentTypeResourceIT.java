package biz.flowinn.product.logistics.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import biz.flowinn.product.logistics.IntegrationTest;
import biz.flowinn.product.logistics.domain.MoloniDocumentType;
import biz.flowinn.product.logistics.domain.enumeration.DocumentType;
import biz.flowinn.product.logistics.repository.MoloniDocumentTypeRepository;
import biz.flowinn.product.logistics.service.dto.MoloniDocumentTypeDTO;
import biz.flowinn.product.logistics.service.mapper.MoloniDocumentTypeMapper;
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
 * Integration tests for the {@link MoloniDocumentTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MoloniDocumentTypeResourceIT {

    private static final DocumentType DEFAULT_DOCUMENT_TYPE = DocumentType.INVOICES;
    private static final DocumentType UPDATED_DOCUMENT_TYPE = DocumentType.RECEIPTS;

    private static final String ENTITY_API_URL = "/api/moloni-document-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MoloniDocumentTypeRepository moloniDocumentTypeRepository;

    @Autowired
    private MoloniDocumentTypeMapper moloniDocumentTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMoloniDocumentTypeMockMvc;

    private MoloniDocumentType moloniDocumentType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MoloniDocumentType createEntity(EntityManager em) {
        MoloniDocumentType moloniDocumentType = new MoloniDocumentType().documentType(DEFAULT_DOCUMENT_TYPE);
        return moloniDocumentType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MoloniDocumentType createUpdatedEntity(EntityManager em) {
        MoloniDocumentType moloniDocumentType = new MoloniDocumentType().documentType(UPDATED_DOCUMENT_TYPE);
        return moloniDocumentType;
    }

    @BeforeEach
    public void initTest() {
        moloniDocumentType = createEntity(em);
    }

    @Test
    @Transactional
    void createMoloniDocumentType() throws Exception {
        int databaseSizeBeforeCreate = moloniDocumentTypeRepository.findAll().size();
        // Create the MoloniDocumentType
        MoloniDocumentTypeDTO moloniDocumentTypeDTO = moloniDocumentTypeMapper.toDto(moloniDocumentType);
        restMoloniDocumentTypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(moloniDocumentTypeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the MoloniDocumentType in the database
        List<MoloniDocumentType> moloniDocumentTypeList = moloniDocumentTypeRepository.findAll();
        assertThat(moloniDocumentTypeList).hasSize(databaseSizeBeforeCreate + 1);
        MoloniDocumentType testMoloniDocumentType = moloniDocumentTypeList.get(moloniDocumentTypeList.size() - 1);
        assertThat(testMoloniDocumentType.getDocumentType()).isEqualTo(DEFAULT_DOCUMENT_TYPE);
    }

    @Test
    @Transactional
    void createMoloniDocumentTypeWithExistingId() throws Exception {
        // Create the MoloniDocumentType with an existing ID
        moloniDocumentType.setId(1L);
        MoloniDocumentTypeDTO moloniDocumentTypeDTO = moloniDocumentTypeMapper.toDto(moloniDocumentType);

        int databaseSizeBeforeCreate = moloniDocumentTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMoloniDocumentTypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(moloniDocumentTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MoloniDocumentType in the database
        List<MoloniDocumentType> moloniDocumentTypeList = moloniDocumentTypeRepository.findAll();
        assertThat(moloniDocumentTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMoloniDocumentTypes() throws Exception {
        // Initialize the database
        moloniDocumentTypeRepository.saveAndFlush(moloniDocumentType);

        // Get all the moloniDocumentTypeList
        restMoloniDocumentTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(moloniDocumentType.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentType").value(hasItem(DEFAULT_DOCUMENT_TYPE.toString())));
    }

    @Test
    @Transactional
    void getMoloniDocumentType() throws Exception {
        // Initialize the database
        moloniDocumentTypeRepository.saveAndFlush(moloniDocumentType);

        // Get the moloniDocumentType
        restMoloniDocumentTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, moloniDocumentType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(moloniDocumentType.getId().intValue()))
            .andExpect(jsonPath("$.documentType").value(DEFAULT_DOCUMENT_TYPE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingMoloniDocumentType() throws Exception {
        // Get the moloniDocumentType
        restMoloniDocumentTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMoloniDocumentType() throws Exception {
        // Initialize the database
        moloniDocumentTypeRepository.saveAndFlush(moloniDocumentType);

        int databaseSizeBeforeUpdate = moloniDocumentTypeRepository.findAll().size();

        // Update the moloniDocumentType
        MoloniDocumentType updatedMoloniDocumentType = moloniDocumentTypeRepository.findById(moloniDocumentType.getId()).get();
        // Disconnect from session so that the updates on updatedMoloniDocumentType are not directly saved in db
        em.detach(updatedMoloniDocumentType);
        updatedMoloniDocumentType.documentType(UPDATED_DOCUMENT_TYPE);
        MoloniDocumentTypeDTO moloniDocumentTypeDTO = moloniDocumentTypeMapper.toDto(updatedMoloniDocumentType);

        restMoloniDocumentTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, moloniDocumentTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(moloniDocumentTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the MoloniDocumentType in the database
        List<MoloniDocumentType> moloniDocumentTypeList = moloniDocumentTypeRepository.findAll();
        assertThat(moloniDocumentTypeList).hasSize(databaseSizeBeforeUpdate);
        MoloniDocumentType testMoloniDocumentType = moloniDocumentTypeList.get(moloniDocumentTypeList.size() - 1);
        assertThat(testMoloniDocumentType.getDocumentType()).isEqualTo(UPDATED_DOCUMENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingMoloniDocumentType() throws Exception {
        int databaseSizeBeforeUpdate = moloniDocumentTypeRepository.findAll().size();
        moloniDocumentType.setId(count.incrementAndGet());

        // Create the MoloniDocumentType
        MoloniDocumentTypeDTO moloniDocumentTypeDTO = moloniDocumentTypeMapper.toDto(moloniDocumentType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMoloniDocumentTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, moloniDocumentTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(moloniDocumentTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MoloniDocumentType in the database
        List<MoloniDocumentType> moloniDocumentTypeList = moloniDocumentTypeRepository.findAll();
        assertThat(moloniDocumentTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMoloniDocumentType() throws Exception {
        int databaseSizeBeforeUpdate = moloniDocumentTypeRepository.findAll().size();
        moloniDocumentType.setId(count.incrementAndGet());

        // Create the MoloniDocumentType
        MoloniDocumentTypeDTO moloniDocumentTypeDTO = moloniDocumentTypeMapper.toDto(moloniDocumentType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMoloniDocumentTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(moloniDocumentTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MoloniDocumentType in the database
        List<MoloniDocumentType> moloniDocumentTypeList = moloniDocumentTypeRepository.findAll();
        assertThat(moloniDocumentTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMoloniDocumentType() throws Exception {
        int databaseSizeBeforeUpdate = moloniDocumentTypeRepository.findAll().size();
        moloniDocumentType.setId(count.incrementAndGet());

        // Create the MoloniDocumentType
        MoloniDocumentTypeDTO moloniDocumentTypeDTO = moloniDocumentTypeMapper.toDto(moloniDocumentType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMoloniDocumentTypeMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(moloniDocumentTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MoloniDocumentType in the database
        List<MoloniDocumentType> moloniDocumentTypeList = moloniDocumentTypeRepository.findAll();
        assertThat(moloniDocumentTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMoloniDocumentTypeWithPatch() throws Exception {
        // Initialize the database
        moloniDocumentTypeRepository.saveAndFlush(moloniDocumentType);

        int databaseSizeBeforeUpdate = moloniDocumentTypeRepository.findAll().size();

        // Update the moloniDocumentType using partial update
        MoloniDocumentType partialUpdatedMoloniDocumentType = new MoloniDocumentType();
        partialUpdatedMoloniDocumentType.setId(moloniDocumentType.getId());

        partialUpdatedMoloniDocumentType.documentType(UPDATED_DOCUMENT_TYPE);

        restMoloniDocumentTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMoloniDocumentType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMoloniDocumentType))
            )
            .andExpect(status().isOk());

        // Validate the MoloniDocumentType in the database
        List<MoloniDocumentType> moloniDocumentTypeList = moloniDocumentTypeRepository.findAll();
        assertThat(moloniDocumentTypeList).hasSize(databaseSizeBeforeUpdate);
        MoloniDocumentType testMoloniDocumentType = moloniDocumentTypeList.get(moloniDocumentTypeList.size() - 1);
        assertThat(testMoloniDocumentType.getDocumentType()).isEqualTo(UPDATED_DOCUMENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateMoloniDocumentTypeWithPatch() throws Exception {
        // Initialize the database
        moloniDocumentTypeRepository.saveAndFlush(moloniDocumentType);

        int databaseSizeBeforeUpdate = moloniDocumentTypeRepository.findAll().size();

        // Update the moloniDocumentType using partial update
        MoloniDocumentType partialUpdatedMoloniDocumentType = new MoloniDocumentType();
        partialUpdatedMoloniDocumentType.setId(moloniDocumentType.getId());

        partialUpdatedMoloniDocumentType.documentType(UPDATED_DOCUMENT_TYPE);

        restMoloniDocumentTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMoloniDocumentType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMoloniDocumentType))
            )
            .andExpect(status().isOk());

        // Validate the MoloniDocumentType in the database
        List<MoloniDocumentType> moloniDocumentTypeList = moloniDocumentTypeRepository.findAll();
        assertThat(moloniDocumentTypeList).hasSize(databaseSizeBeforeUpdate);
        MoloniDocumentType testMoloniDocumentType = moloniDocumentTypeList.get(moloniDocumentTypeList.size() - 1);
        assertThat(testMoloniDocumentType.getDocumentType()).isEqualTo(UPDATED_DOCUMENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingMoloniDocumentType() throws Exception {
        int databaseSizeBeforeUpdate = moloniDocumentTypeRepository.findAll().size();
        moloniDocumentType.setId(count.incrementAndGet());

        // Create the MoloniDocumentType
        MoloniDocumentTypeDTO moloniDocumentTypeDTO = moloniDocumentTypeMapper.toDto(moloniDocumentType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMoloniDocumentTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, moloniDocumentTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(moloniDocumentTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MoloniDocumentType in the database
        List<MoloniDocumentType> moloniDocumentTypeList = moloniDocumentTypeRepository.findAll();
        assertThat(moloniDocumentTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMoloniDocumentType() throws Exception {
        int databaseSizeBeforeUpdate = moloniDocumentTypeRepository.findAll().size();
        moloniDocumentType.setId(count.incrementAndGet());

        // Create the MoloniDocumentType
        MoloniDocumentTypeDTO moloniDocumentTypeDTO = moloniDocumentTypeMapper.toDto(moloniDocumentType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMoloniDocumentTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(moloniDocumentTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MoloniDocumentType in the database
        List<MoloniDocumentType> moloniDocumentTypeList = moloniDocumentTypeRepository.findAll();
        assertThat(moloniDocumentTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMoloniDocumentType() throws Exception {
        int databaseSizeBeforeUpdate = moloniDocumentTypeRepository.findAll().size();
        moloniDocumentType.setId(count.incrementAndGet());

        // Create the MoloniDocumentType
        MoloniDocumentTypeDTO moloniDocumentTypeDTO = moloniDocumentTypeMapper.toDto(moloniDocumentType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMoloniDocumentTypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(moloniDocumentTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MoloniDocumentType in the database
        List<MoloniDocumentType> moloniDocumentTypeList = moloniDocumentTypeRepository.findAll();
        assertThat(moloniDocumentTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMoloniDocumentType() throws Exception {
        // Initialize the database
        moloniDocumentTypeRepository.saveAndFlush(moloniDocumentType);

        int databaseSizeBeforeDelete = moloniDocumentTypeRepository.findAll().size();

        // Delete the moloniDocumentType
        restMoloniDocumentTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, moloniDocumentType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MoloniDocumentType> moloniDocumentTypeList = moloniDocumentTypeRepository.findAll();
        assertThat(moloniDocumentTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
