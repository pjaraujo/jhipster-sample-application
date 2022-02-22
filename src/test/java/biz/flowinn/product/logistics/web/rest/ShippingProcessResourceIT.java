package biz.flowinn.product.logistics.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import biz.flowinn.product.logistics.IntegrationTest;
import biz.flowinn.product.logistics.domain.ShippingProcess;
import biz.flowinn.product.logistics.repository.ShippingProcessRepository;
import biz.flowinn.product.logistics.service.dto.ShippingProcessDTO;
import biz.flowinn.product.logistics.service.mapper.ShippingProcessMapper;
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
 * Integration tests for the {@link ShippingProcessResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ShippingProcessResourceIT {

    private static final String ENTITY_API_URL = "/api/shipping-processes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ShippingProcessRepository shippingProcessRepository;

    @Autowired
    private ShippingProcessMapper shippingProcessMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restShippingProcessMockMvc;

    private ShippingProcess shippingProcess;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShippingProcess createEntity(EntityManager em) {
        ShippingProcess shippingProcess = new ShippingProcess();
        return shippingProcess;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShippingProcess createUpdatedEntity(EntityManager em) {
        ShippingProcess shippingProcess = new ShippingProcess();
        return shippingProcess;
    }

    @BeforeEach
    public void initTest() {
        shippingProcess = createEntity(em);
    }

    @Test
    @Transactional
    void createShippingProcess() throws Exception {
        int databaseSizeBeforeCreate = shippingProcessRepository.findAll().size();
        // Create the ShippingProcess
        ShippingProcessDTO shippingProcessDTO = shippingProcessMapper.toDto(shippingProcess);
        restShippingProcessMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shippingProcessDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ShippingProcess in the database
        List<ShippingProcess> shippingProcessList = shippingProcessRepository.findAll();
        assertThat(shippingProcessList).hasSize(databaseSizeBeforeCreate + 1);
        ShippingProcess testShippingProcess = shippingProcessList.get(shippingProcessList.size() - 1);
    }

    @Test
    @Transactional
    void createShippingProcessWithExistingId() throws Exception {
        // Create the ShippingProcess with an existing ID
        shippingProcess.setId(1L);
        ShippingProcessDTO shippingProcessDTO = shippingProcessMapper.toDto(shippingProcess);

        int databaseSizeBeforeCreate = shippingProcessRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restShippingProcessMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shippingProcessDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShippingProcess in the database
        List<ShippingProcess> shippingProcessList = shippingProcessRepository.findAll();
        assertThat(shippingProcessList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllShippingProcesses() throws Exception {
        // Initialize the database
        shippingProcessRepository.saveAndFlush(shippingProcess);

        // Get all the shippingProcessList
        restShippingProcessMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shippingProcess.getId().intValue())));
    }

    @Test
    @Transactional
    void getShippingProcess() throws Exception {
        // Initialize the database
        shippingProcessRepository.saveAndFlush(shippingProcess);

        // Get the shippingProcess
        restShippingProcessMockMvc
            .perform(get(ENTITY_API_URL_ID, shippingProcess.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(shippingProcess.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingShippingProcess() throws Exception {
        // Get the shippingProcess
        restShippingProcessMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewShippingProcess() throws Exception {
        // Initialize the database
        shippingProcessRepository.saveAndFlush(shippingProcess);

        int databaseSizeBeforeUpdate = shippingProcessRepository.findAll().size();

        // Update the shippingProcess
        ShippingProcess updatedShippingProcess = shippingProcessRepository.findById(shippingProcess.getId()).get();
        // Disconnect from session so that the updates on updatedShippingProcess are not directly saved in db
        em.detach(updatedShippingProcess);
        ShippingProcessDTO shippingProcessDTO = shippingProcessMapper.toDto(updatedShippingProcess);

        restShippingProcessMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shippingProcessDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shippingProcessDTO))
            )
            .andExpect(status().isOk());

        // Validate the ShippingProcess in the database
        List<ShippingProcess> shippingProcessList = shippingProcessRepository.findAll();
        assertThat(shippingProcessList).hasSize(databaseSizeBeforeUpdate);
        ShippingProcess testShippingProcess = shippingProcessList.get(shippingProcessList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingShippingProcess() throws Exception {
        int databaseSizeBeforeUpdate = shippingProcessRepository.findAll().size();
        shippingProcess.setId(count.incrementAndGet());

        // Create the ShippingProcess
        ShippingProcessDTO shippingProcessDTO = shippingProcessMapper.toDto(shippingProcess);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShippingProcessMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shippingProcessDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shippingProcessDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShippingProcess in the database
        List<ShippingProcess> shippingProcessList = shippingProcessRepository.findAll();
        assertThat(shippingProcessList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchShippingProcess() throws Exception {
        int databaseSizeBeforeUpdate = shippingProcessRepository.findAll().size();
        shippingProcess.setId(count.incrementAndGet());

        // Create the ShippingProcess
        ShippingProcessDTO shippingProcessDTO = shippingProcessMapper.toDto(shippingProcess);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShippingProcessMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shippingProcessDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShippingProcess in the database
        List<ShippingProcess> shippingProcessList = shippingProcessRepository.findAll();
        assertThat(shippingProcessList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamShippingProcess() throws Exception {
        int databaseSizeBeforeUpdate = shippingProcessRepository.findAll().size();
        shippingProcess.setId(count.incrementAndGet());

        // Create the ShippingProcess
        ShippingProcessDTO shippingProcessDTO = shippingProcessMapper.toDto(shippingProcess);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShippingProcessMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shippingProcessDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ShippingProcess in the database
        List<ShippingProcess> shippingProcessList = shippingProcessRepository.findAll();
        assertThat(shippingProcessList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateShippingProcessWithPatch() throws Exception {
        // Initialize the database
        shippingProcessRepository.saveAndFlush(shippingProcess);

        int databaseSizeBeforeUpdate = shippingProcessRepository.findAll().size();

        // Update the shippingProcess using partial update
        ShippingProcess partialUpdatedShippingProcess = new ShippingProcess();
        partialUpdatedShippingProcess.setId(shippingProcess.getId());

        restShippingProcessMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShippingProcess.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedShippingProcess))
            )
            .andExpect(status().isOk());

        // Validate the ShippingProcess in the database
        List<ShippingProcess> shippingProcessList = shippingProcessRepository.findAll();
        assertThat(shippingProcessList).hasSize(databaseSizeBeforeUpdate);
        ShippingProcess testShippingProcess = shippingProcessList.get(shippingProcessList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateShippingProcessWithPatch() throws Exception {
        // Initialize the database
        shippingProcessRepository.saveAndFlush(shippingProcess);

        int databaseSizeBeforeUpdate = shippingProcessRepository.findAll().size();

        // Update the shippingProcess using partial update
        ShippingProcess partialUpdatedShippingProcess = new ShippingProcess();
        partialUpdatedShippingProcess.setId(shippingProcess.getId());

        restShippingProcessMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShippingProcess.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedShippingProcess))
            )
            .andExpect(status().isOk());

        // Validate the ShippingProcess in the database
        List<ShippingProcess> shippingProcessList = shippingProcessRepository.findAll();
        assertThat(shippingProcessList).hasSize(databaseSizeBeforeUpdate);
        ShippingProcess testShippingProcess = shippingProcessList.get(shippingProcessList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingShippingProcess() throws Exception {
        int databaseSizeBeforeUpdate = shippingProcessRepository.findAll().size();
        shippingProcess.setId(count.incrementAndGet());

        // Create the ShippingProcess
        ShippingProcessDTO shippingProcessDTO = shippingProcessMapper.toDto(shippingProcess);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShippingProcessMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, shippingProcessDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(shippingProcessDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShippingProcess in the database
        List<ShippingProcess> shippingProcessList = shippingProcessRepository.findAll();
        assertThat(shippingProcessList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchShippingProcess() throws Exception {
        int databaseSizeBeforeUpdate = shippingProcessRepository.findAll().size();
        shippingProcess.setId(count.incrementAndGet());

        // Create the ShippingProcess
        ShippingProcessDTO shippingProcessDTO = shippingProcessMapper.toDto(shippingProcess);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShippingProcessMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(shippingProcessDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShippingProcess in the database
        List<ShippingProcess> shippingProcessList = shippingProcessRepository.findAll();
        assertThat(shippingProcessList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamShippingProcess() throws Exception {
        int databaseSizeBeforeUpdate = shippingProcessRepository.findAll().size();
        shippingProcess.setId(count.incrementAndGet());

        // Create the ShippingProcess
        ShippingProcessDTO shippingProcessDTO = shippingProcessMapper.toDto(shippingProcess);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShippingProcessMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(shippingProcessDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ShippingProcess in the database
        List<ShippingProcess> shippingProcessList = shippingProcessRepository.findAll();
        assertThat(shippingProcessList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteShippingProcess() throws Exception {
        // Initialize the database
        shippingProcessRepository.saveAndFlush(shippingProcess);

        int databaseSizeBeforeDelete = shippingProcessRepository.findAll().size();

        // Delete the shippingProcess
        restShippingProcessMockMvc
            .perform(delete(ENTITY_API_URL_ID, shippingProcess.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ShippingProcess> shippingProcessList = shippingProcessRepository.findAll();
        assertThat(shippingProcessList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
