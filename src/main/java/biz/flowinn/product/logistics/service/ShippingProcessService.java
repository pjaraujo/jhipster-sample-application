package biz.flowinn.product.logistics.service;

import biz.flowinn.product.logistics.domain.ShippingProcess;
import biz.flowinn.product.logistics.repository.ShippingProcessRepository;
import biz.flowinn.product.logistics.service.dto.ShippingProcessDTO;
import biz.flowinn.product.logistics.service.mapper.ShippingProcessMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ShippingProcess}.
 */
@Service
@Transactional
public class ShippingProcessService {

    private final Logger log = LoggerFactory.getLogger(ShippingProcessService.class);

    private final ShippingProcessRepository shippingProcessRepository;

    private final ShippingProcessMapper shippingProcessMapper;

    public ShippingProcessService(ShippingProcessRepository shippingProcessRepository, ShippingProcessMapper shippingProcessMapper) {
        this.shippingProcessRepository = shippingProcessRepository;
        this.shippingProcessMapper = shippingProcessMapper;
    }

    /**
     * Save a shippingProcess.
     *
     * @param shippingProcessDTO the entity to save.
     * @return the persisted entity.
     */
    public ShippingProcessDTO save(ShippingProcessDTO shippingProcessDTO) {
        log.debug("Request to save ShippingProcess : {}", shippingProcessDTO);
        ShippingProcess shippingProcess = shippingProcessMapper.toEntity(shippingProcessDTO);
        shippingProcess = shippingProcessRepository.save(shippingProcess);
        return shippingProcessMapper.toDto(shippingProcess);
    }

    /**
     * Partially update a shippingProcess.
     *
     * @param shippingProcessDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ShippingProcessDTO> partialUpdate(ShippingProcessDTO shippingProcessDTO) {
        log.debug("Request to partially update ShippingProcess : {}", shippingProcessDTO);

        return shippingProcessRepository
            .findById(shippingProcessDTO.getId())
            .map(existingShippingProcess -> {
                shippingProcessMapper.partialUpdate(existingShippingProcess, shippingProcessDTO);

                return existingShippingProcess;
            })
            .map(shippingProcessRepository::save)
            .map(shippingProcessMapper::toDto);
    }

    /**
     * Get all the shippingProcesses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ShippingProcessDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ShippingProcesses");
        return shippingProcessRepository.findAll(pageable).map(shippingProcessMapper::toDto);
    }

    /**
     * Get one shippingProcess by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ShippingProcessDTO> findOne(Long id) {
        log.debug("Request to get ShippingProcess : {}", id);
        return shippingProcessRepository.findById(id).map(shippingProcessMapper::toDto);
    }

    /**
     * Delete the shippingProcess by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ShippingProcess : {}", id);
        shippingProcessRepository.deleteById(id);
    }
}
