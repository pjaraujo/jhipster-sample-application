package biz.flowinn.product.logistics.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import biz.flowinn.product.logistics.IntegrationTest;
import biz.flowinn.product.logistics.domain.MoloniConfiguration;
import biz.flowinn.product.logistics.repository.MoloniConfigurationRepository;
import biz.flowinn.product.logistics.service.dto.MoloniConfigurationDTO;
import biz.flowinn.product.logistics.service.mapper.MoloniConfigurationMapper;
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
 * Integration tests for the {@link MoloniConfigurationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MoloniConfigurationResourceIT {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT = "BBBBBBBBBB";

    private static final String DEFAULT_SECRET = "AAAAAAAAAA";
    private static final String UPDATED_SECRET = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/moloni-configurations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MoloniConfigurationRepository moloniConfigurationRepository;

    @Autowired
    private MoloniConfigurationMapper moloniConfigurationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMoloniConfigurationMockMvc;

    private MoloniConfiguration moloniConfiguration;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MoloniConfiguration createEntity(EntityManager em) {
        MoloniConfiguration moloniConfiguration = new MoloniConfiguration()
            .username(DEFAULT_USERNAME)
            .password(DEFAULT_PASSWORD)
            .company(DEFAULT_COMPANY)
            .client(DEFAULT_CLIENT)
            .secret(DEFAULT_SECRET);
        return moloniConfiguration;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MoloniConfiguration createUpdatedEntity(EntityManager em) {
        MoloniConfiguration moloniConfiguration = new MoloniConfiguration()
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .company(UPDATED_COMPANY)
            .client(UPDATED_CLIENT)
            .secret(UPDATED_SECRET);
        return moloniConfiguration;
    }

    @BeforeEach
    public void initTest() {
        moloniConfiguration = createEntity(em);
    }

    @Test
    @Transactional
    void createMoloniConfiguration() throws Exception {
        int databaseSizeBeforeCreate = moloniConfigurationRepository.findAll().size();
        // Create the MoloniConfiguration
        MoloniConfigurationDTO moloniConfigurationDTO = moloniConfigurationMapper.toDto(moloniConfiguration);
        restMoloniConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(moloniConfigurationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the MoloniConfiguration in the database
        List<MoloniConfiguration> moloniConfigurationList = moloniConfigurationRepository.findAll();
        assertThat(moloniConfigurationList).hasSize(databaseSizeBeforeCreate + 1);
        MoloniConfiguration testMoloniConfiguration = moloniConfigurationList.get(moloniConfigurationList.size() - 1);
        assertThat(testMoloniConfiguration.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testMoloniConfiguration.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testMoloniConfiguration.getCompany()).isEqualTo(DEFAULT_COMPANY);
        assertThat(testMoloniConfiguration.getClient()).isEqualTo(DEFAULT_CLIENT);
        assertThat(testMoloniConfiguration.getSecret()).isEqualTo(DEFAULT_SECRET);
    }

    @Test
    @Transactional
    void createMoloniConfigurationWithExistingId() throws Exception {
        // Create the MoloniConfiguration with an existing ID
        moloniConfiguration.setId(1L);
        MoloniConfigurationDTO moloniConfigurationDTO = moloniConfigurationMapper.toDto(moloniConfiguration);

        int databaseSizeBeforeCreate = moloniConfigurationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMoloniConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(moloniConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MoloniConfiguration in the database
        List<MoloniConfiguration> moloniConfigurationList = moloniConfigurationRepository.findAll();
        assertThat(moloniConfigurationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMoloniConfigurations() throws Exception {
        // Initialize the database
        moloniConfigurationRepository.saveAndFlush(moloniConfiguration);

        // Get all the moloniConfigurationList
        restMoloniConfigurationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(moloniConfiguration.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY)))
            .andExpect(jsonPath("$.[*].client").value(hasItem(DEFAULT_CLIENT)))
            .andExpect(jsonPath("$.[*].secret").value(hasItem(DEFAULT_SECRET)));
    }

    @Test
    @Transactional
    void getMoloniConfiguration() throws Exception {
        // Initialize the database
        moloniConfigurationRepository.saveAndFlush(moloniConfiguration);

        // Get the moloniConfiguration
        restMoloniConfigurationMockMvc
            .perform(get(ENTITY_API_URL_ID, moloniConfiguration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(moloniConfiguration.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.company").value(DEFAULT_COMPANY))
            .andExpect(jsonPath("$.client").value(DEFAULT_CLIENT))
            .andExpect(jsonPath("$.secret").value(DEFAULT_SECRET));
    }

    @Test
    @Transactional
    void getNonExistingMoloniConfiguration() throws Exception {
        // Get the moloniConfiguration
        restMoloniConfigurationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMoloniConfiguration() throws Exception {
        // Initialize the database
        moloniConfigurationRepository.saveAndFlush(moloniConfiguration);

        int databaseSizeBeforeUpdate = moloniConfigurationRepository.findAll().size();

        // Update the moloniConfiguration
        MoloniConfiguration updatedMoloniConfiguration = moloniConfigurationRepository.findById(moloniConfiguration.getId()).get();
        // Disconnect from session so that the updates on updatedMoloniConfiguration are not directly saved in db
        em.detach(updatedMoloniConfiguration);
        updatedMoloniConfiguration
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .company(UPDATED_COMPANY)
            .client(UPDATED_CLIENT)
            .secret(UPDATED_SECRET);
        MoloniConfigurationDTO moloniConfigurationDTO = moloniConfigurationMapper.toDto(updatedMoloniConfiguration);

        restMoloniConfigurationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, moloniConfigurationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(moloniConfigurationDTO))
            )
            .andExpect(status().isOk());

        // Validate the MoloniConfiguration in the database
        List<MoloniConfiguration> moloniConfigurationList = moloniConfigurationRepository.findAll();
        assertThat(moloniConfigurationList).hasSize(databaseSizeBeforeUpdate);
        MoloniConfiguration testMoloniConfiguration = moloniConfigurationList.get(moloniConfigurationList.size() - 1);
        assertThat(testMoloniConfiguration.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testMoloniConfiguration.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testMoloniConfiguration.getCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testMoloniConfiguration.getClient()).isEqualTo(UPDATED_CLIENT);
        assertThat(testMoloniConfiguration.getSecret()).isEqualTo(UPDATED_SECRET);
    }

    @Test
    @Transactional
    void putNonExistingMoloniConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = moloniConfigurationRepository.findAll().size();
        moloniConfiguration.setId(count.incrementAndGet());

        // Create the MoloniConfiguration
        MoloniConfigurationDTO moloniConfigurationDTO = moloniConfigurationMapper.toDto(moloniConfiguration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMoloniConfigurationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, moloniConfigurationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(moloniConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MoloniConfiguration in the database
        List<MoloniConfiguration> moloniConfigurationList = moloniConfigurationRepository.findAll();
        assertThat(moloniConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMoloniConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = moloniConfigurationRepository.findAll().size();
        moloniConfiguration.setId(count.incrementAndGet());

        // Create the MoloniConfiguration
        MoloniConfigurationDTO moloniConfigurationDTO = moloniConfigurationMapper.toDto(moloniConfiguration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMoloniConfigurationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(moloniConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MoloniConfiguration in the database
        List<MoloniConfiguration> moloniConfigurationList = moloniConfigurationRepository.findAll();
        assertThat(moloniConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMoloniConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = moloniConfigurationRepository.findAll().size();
        moloniConfiguration.setId(count.incrementAndGet());

        // Create the MoloniConfiguration
        MoloniConfigurationDTO moloniConfigurationDTO = moloniConfigurationMapper.toDto(moloniConfiguration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMoloniConfigurationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(moloniConfigurationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MoloniConfiguration in the database
        List<MoloniConfiguration> moloniConfigurationList = moloniConfigurationRepository.findAll();
        assertThat(moloniConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMoloniConfigurationWithPatch() throws Exception {
        // Initialize the database
        moloniConfigurationRepository.saveAndFlush(moloniConfiguration);

        int databaseSizeBeforeUpdate = moloniConfigurationRepository.findAll().size();

        // Update the moloniConfiguration using partial update
        MoloniConfiguration partialUpdatedMoloniConfiguration = new MoloniConfiguration();
        partialUpdatedMoloniConfiguration.setId(moloniConfiguration.getId());

        partialUpdatedMoloniConfiguration.username(UPDATED_USERNAME).company(UPDATED_COMPANY);

        restMoloniConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMoloniConfiguration.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMoloniConfiguration))
            )
            .andExpect(status().isOk());

        // Validate the MoloniConfiguration in the database
        List<MoloniConfiguration> moloniConfigurationList = moloniConfigurationRepository.findAll();
        assertThat(moloniConfigurationList).hasSize(databaseSizeBeforeUpdate);
        MoloniConfiguration testMoloniConfiguration = moloniConfigurationList.get(moloniConfigurationList.size() - 1);
        assertThat(testMoloniConfiguration.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testMoloniConfiguration.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testMoloniConfiguration.getCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testMoloniConfiguration.getClient()).isEqualTo(DEFAULT_CLIENT);
        assertThat(testMoloniConfiguration.getSecret()).isEqualTo(DEFAULT_SECRET);
    }

    @Test
    @Transactional
    void fullUpdateMoloniConfigurationWithPatch() throws Exception {
        // Initialize the database
        moloniConfigurationRepository.saveAndFlush(moloniConfiguration);

        int databaseSizeBeforeUpdate = moloniConfigurationRepository.findAll().size();

        // Update the moloniConfiguration using partial update
        MoloniConfiguration partialUpdatedMoloniConfiguration = new MoloniConfiguration();
        partialUpdatedMoloniConfiguration.setId(moloniConfiguration.getId());

        partialUpdatedMoloniConfiguration
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .company(UPDATED_COMPANY)
            .client(UPDATED_CLIENT)
            .secret(UPDATED_SECRET);

        restMoloniConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMoloniConfiguration.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMoloniConfiguration))
            )
            .andExpect(status().isOk());

        // Validate the MoloniConfiguration in the database
        List<MoloniConfiguration> moloniConfigurationList = moloniConfigurationRepository.findAll();
        assertThat(moloniConfigurationList).hasSize(databaseSizeBeforeUpdate);
        MoloniConfiguration testMoloniConfiguration = moloniConfigurationList.get(moloniConfigurationList.size() - 1);
        assertThat(testMoloniConfiguration.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testMoloniConfiguration.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testMoloniConfiguration.getCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testMoloniConfiguration.getClient()).isEqualTo(UPDATED_CLIENT);
        assertThat(testMoloniConfiguration.getSecret()).isEqualTo(UPDATED_SECRET);
    }

    @Test
    @Transactional
    void patchNonExistingMoloniConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = moloniConfigurationRepository.findAll().size();
        moloniConfiguration.setId(count.incrementAndGet());

        // Create the MoloniConfiguration
        MoloniConfigurationDTO moloniConfigurationDTO = moloniConfigurationMapper.toDto(moloniConfiguration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMoloniConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, moloniConfigurationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(moloniConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MoloniConfiguration in the database
        List<MoloniConfiguration> moloniConfigurationList = moloniConfigurationRepository.findAll();
        assertThat(moloniConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMoloniConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = moloniConfigurationRepository.findAll().size();
        moloniConfiguration.setId(count.incrementAndGet());

        // Create the MoloniConfiguration
        MoloniConfigurationDTO moloniConfigurationDTO = moloniConfigurationMapper.toDto(moloniConfiguration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMoloniConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(moloniConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MoloniConfiguration in the database
        List<MoloniConfiguration> moloniConfigurationList = moloniConfigurationRepository.findAll();
        assertThat(moloniConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMoloniConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = moloniConfigurationRepository.findAll().size();
        moloniConfiguration.setId(count.incrementAndGet());

        // Create the MoloniConfiguration
        MoloniConfigurationDTO moloniConfigurationDTO = moloniConfigurationMapper.toDto(moloniConfiguration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMoloniConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(moloniConfigurationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MoloniConfiguration in the database
        List<MoloniConfiguration> moloniConfigurationList = moloniConfigurationRepository.findAll();
        assertThat(moloniConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMoloniConfiguration() throws Exception {
        // Initialize the database
        moloniConfigurationRepository.saveAndFlush(moloniConfiguration);

        int databaseSizeBeforeDelete = moloniConfigurationRepository.findAll().size();

        // Delete the moloniConfiguration
        restMoloniConfigurationMockMvc
            .perform(delete(ENTITY_API_URL_ID, moloniConfiguration.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MoloniConfiguration> moloniConfigurationList = moloniConfigurationRepository.findAll();
        assertThat(moloniConfigurationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
