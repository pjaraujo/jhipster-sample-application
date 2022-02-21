package biz.flowinn.product.logistics.service;

import biz.flowinn.product.logistics.domain.MoloniConfiguration;
import biz.flowinn.product.logistics.repository.MoloniConfigurationRepository;
import biz.flowinn.product.logistics.service.dto.MoloniConfigurationDTO;
import biz.flowinn.product.logistics.service.mapper.MoloniConfigurationMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MoloniConfiguration}.
 */
@Service
@Transactional
public class MoloniConfigurationService {

    private final Logger log = LoggerFactory.getLogger(MoloniConfigurationService.class);

    private final MoloniConfigurationRepository moloniConfigurationRepository;

    private final MoloniConfigurationMapper moloniConfigurationMapper;

    public MoloniConfigurationService(
        MoloniConfigurationRepository moloniConfigurationRepository,
        MoloniConfigurationMapper moloniConfigurationMapper
    ) {
        this.moloniConfigurationRepository = moloniConfigurationRepository;
        this.moloniConfigurationMapper = moloniConfigurationMapper;
    }

    /**
     * Save a moloniConfiguration.
     *
     * @param moloniConfigurationDTO the entity to save.
     * @return the persisted entity.
     */
    public MoloniConfigurationDTO save(MoloniConfigurationDTO moloniConfigurationDTO) {
        log.debug("Request to save MoloniConfiguration : {}", moloniConfigurationDTO);
        MoloniConfiguration moloniConfiguration = moloniConfigurationMapper.toEntity(moloniConfigurationDTO);
        moloniConfiguration = moloniConfigurationRepository.save(moloniConfiguration);
        return moloniConfigurationMapper.toDto(moloniConfiguration);
    }

    /**
     * Partially update a moloniConfiguration.
     *
     * @param moloniConfigurationDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MoloniConfigurationDTO> partialUpdate(MoloniConfigurationDTO moloniConfigurationDTO) {
        log.debug("Request to partially update MoloniConfiguration : {}", moloniConfigurationDTO);

        return moloniConfigurationRepository
            .findById(moloniConfigurationDTO.getId())
            .map(existingMoloniConfiguration -> {
                moloniConfigurationMapper.partialUpdate(existingMoloniConfiguration, moloniConfigurationDTO);

                return existingMoloniConfiguration;
            })
            .map(moloniConfigurationRepository::save)
            .map(moloniConfigurationMapper::toDto);
    }

    /**
     * Get all the moloniConfigurations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MoloniConfigurationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MoloniConfigurations");
        return moloniConfigurationRepository.findAll(pageable).map(moloniConfigurationMapper::toDto);
    }

    /**
     * Get one moloniConfiguration by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MoloniConfigurationDTO> findOne(Long id) {
        log.debug("Request to get MoloniConfiguration : {}", id);
        return moloniConfigurationRepository.findById(id).map(moloniConfigurationMapper::toDto);
    }

    /**
     * Delete the moloniConfiguration by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MoloniConfiguration : {}", id);
        moloniConfigurationRepository.deleteById(id);
    }
}
