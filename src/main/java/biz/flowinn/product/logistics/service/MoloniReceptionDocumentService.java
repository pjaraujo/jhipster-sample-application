package biz.flowinn.product.logistics.service;

import biz.flowinn.product.logistics.domain.MoloniReceptionDocument;
import biz.flowinn.product.logistics.repository.MoloniReceptionDocumentRepository;
import biz.flowinn.product.logistics.service.dto.MoloniReceptionDocumentDTO;
import biz.flowinn.product.logistics.service.mapper.MoloniReceptionDocumentMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MoloniReceptionDocument}.
 */
@Service
@Transactional
public class MoloniReceptionDocumentService {

    private final Logger log = LoggerFactory.getLogger(MoloniReceptionDocumentService.class);

    private final MoloniReceptionDocumentRepository moloniReceptionDocumentRepository;

    private final MoloniReceptionDocumentMapper moloniReceptionDocumentMapper;

    public MoloniReceptionDocumentService(
        MoloniReceptionDocumentRepository moloniReceptionDocumentRepository,
        MoloniReceptionDocumentMapper moloniReceptionDocumentMapper
    ) {
        this.moloniReceptionDocumentRepository = moloniReceptionDocumentRepository;
        this.moloniReceptionDocumentMapper = moloniReceptionDocumentMapper;
    }

    /**
     * Save a moloniReceptionDocument.
     *
     * @param moloniReceptionDocumentDTO the entity to save.
     * @return the persisted entity.
     */
    public MoloniReceptionDocumentDTO save(MoloniReceptionDocumentDTO moloniReceptionDocumentDTO) {
        log.debug("Request to save MoloniReceptionDocument : {}", moloniReceptionDocumentDTO);
        MoloniReceptionDocument moloniReceptionDocument = moloniReceptionDocumentMapper.toEntity(moloniReceptionDocumentDTO);
        moloniReceptionDocument = moloniReceptionDocumentRepository.save(moloniReceptionDocument);
        return moloniReceptionDocumentMapper.toDto(moloniReceptionDocument);
    }

    /**
     * Partially update a moloniReceptionDocument.
     *
     * @param moloniReceptionDocumentDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MoloniReceptionDocumentDTO> partialUpdate(MoloniReceptionDocumentDTO moloniReceptionDocumentDTO) {
        log.debug("Request to partially update MoloniReceptionDocument : {}", moloniReceptionDocumentDTO);

        return moloniReceptionDocumentRepository
            .findById(moloniReceptionDocumentDTO.getId())
            .map(existingMoloniReceptionDocument -> {
                moloniReceptionDocumentMapper.partialUpdate(existingMoloniReceptionDocument, moloniReceptionDocumentDTO);

                return existingMoloniReceptionDocument;
            })
            .map(moloniReceptionDocumentRepository::save)
            .map(moloniReceptionDocumentMapper::toDto);
    }

    /**
     * Get all the moloniReceptionDocuments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MoloniReceptionDocumentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MoloniReceptionDocuments");
        return moloniReceptionDocumentRepository.findAll(pageable).map(moloniReceptionDocumentMapper::toDto);
    }

    /**
     * Get one moloniReceptionDocument by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MoloniReceptionDocumentDTO> findOne(Long id) {
        log.debug("Request to get MoloniReceptionDocument : {}", id);
        return moloniReceptionDocumentRepository.findById(id).map(moloniReceptionDocumentMapper::toDto);
    }

    /**
     * Delete the moloniReceptionDocument by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MoloniReceptionDocument : {}", id);
        moloniReceptionDocumentRepository.deleteById(id);
    }
}
