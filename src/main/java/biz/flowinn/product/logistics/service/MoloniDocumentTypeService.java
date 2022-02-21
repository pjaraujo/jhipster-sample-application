package biz.flowinn.product.logistics.service;

import biz.flowinn.product.logistics.domain.MoloniDocumentType;
import biz.flowinn.product.logistics.repository.MoloniDocumentTypeRepository;
import biz.flowinn.product.logistics.service.dto.MoloniDocumentTypeDTO;
import biz.flowinn.product.logistics.service.mapper.MoloniDocumentTypeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MoloniDocumentType}.
 */
@Service
@Transactional
public class MoloniDocumentTypeService {

    private final Logger log = LoggerFactory.getLogger(MoloniDocumentTypeService.class);

    private final MoloniDocumentTypeRepository moloniDocumentTypeRepository;

    private final MoloniDocumentTypeMapper moloniDocumentTypeMapper;

    public MoloniDocumentTypeService(
        MoloniDocumentTypeRepository moloniDocumentTypeRepository,
        MoloniDocumentTypeMapper moloniDocumentTypeMapper
    ) {
        this.moloniDocumentTypeRepository = moloniDocumentTypeRepository;
        this.moloniDocumentTypeMapper = moloniDocumentTypeMapper;
    }

    /**
     * Save a moloniDocumentType.
     *
     * @param moloniDocumentTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public MoloniDocumentTypeDTO save(MoloniDocumentTypeDTO moloniDocumentTypeDTO) {
        log.debug("Request to save MoloniDocumentType : {}", moloniDocumentTypeDTO);
        MoloniDocumentType moloniDocumentType = moloniDocumentTypeMapper.toEntity(moloniDocumentTypeDTO);
        moloniDocumentType = moloniDocumentTypeRepository.save(moloniDocumentType);
        return moloniDocumentTypeMapper.toDto(moloniDocumentType);
    }

    /**
     * Partially update a moloniDocumentType.
     *
     * @param moloniDocumentTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MoloniDocumentTypeDTO> partialUpdate(MoloniDocumentTypeDTO moloniDocumentTypeDTO) {
        log.debug("Request to partially update MoloniDocumentType : {}", moloniDocumentTypeDTO);

        return moloniDocumentTypeRepository
            .findById(moloniDocumentTypeDTO.getId())
            .map(existingMoloniDocumentType -> {
                moloniDocumentTypeMapper.partialUpdate(existingMoloniDocumentType, moloniDocumentTypeDTO);

                return existingMoloniDocumentType;
            })
            .map(moloniDocumentTypeRepository::save)
            .map(moloniDocumentTypeMapper::toDto);
    }

    /**
     * Get all the moloniDocumentTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MoloniDocumentTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MoloniDocumentTypes");
        return moloniDocumentTypeRepository.findAll(pageable).map(moloniDocumentTypeMapper::toDto);
    }

    /**
     * Get one moloniDocumentType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MoloniDocumentTypeDTO> findOne(Long id) {
        log.debug("Request to get MoloniDocumentType : {}", id);
        return moloniDocumentTypeRepository.findById(id).map(moloniDocumentTypeMapper::toDto);
    }

    /**
     * Delete the moloniDocumentType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MoloniDocumentType : {}", id);
        moloniDocumentTypeRepository.deleteById(id);
    }
}
