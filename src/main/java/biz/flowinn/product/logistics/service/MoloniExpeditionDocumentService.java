package biz.flowinn.product.logistics.service;

import biz.flowinn.product.logistics.domain.MoloniExpeditionDocument;
import biz.flowinn.product.logistics.repository.MoloniExpeditionDocumentRepository;
import biz.flowinn.product.logistics.service.dto.MoloniExpeditionDocumentDTO;
import biz.flowinn.product.logistics.service.mapper.MoloniExpeditionDocumentMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MoloniExpeditionDocument}.
 */
@Service
@Transactional
public class MoloniExpeditionDocumentService {

    private final Logger log = LoggerFactory.getLogger(MoloniExpeditionDocumentService.class);

    private final MoloniExpeditionDocumentRepository moloniExpeditionDocumentRepository;

    private final MoloniExpeditionDocumentMapper moloniExpeditionDocumentMapper;

    public MoloniExpeditionDocumentService(
        MoloniExpeditionDocumentRepository moloniExpeditionDocumentRepository,
        MoloniExpeditionDocumentMapper moloniExpeditionDocumentMapper
    ) {
        this.moloniExpeditionDocumentRepository = moloniExpeditionDocumentRepository;
        this.moloniExpeditionDocumentMapper = moloniExpeditionDocumentMapper;
    }

    /**
     * Save a moloniExpeditionDocument.
     *
     * @param moloniExpeditionDocumentDTO the entity to save.
     * @return the persisted entity.
     */
    public MoloniExpeditionDocumentDTO save(MoloniExpeditionDocumentDTO moloniExpeditionDocumentDTO) {
        log.debug("Request to save MoloniExpeditionDocument : {}", moloniExpeditionDocumentDTO);
        MoloniExpeditionDocument moloniExpeditionDocument = moloniExpeditionDocumentMapper.toEntity(moloniExpeditionDocumentDTO);
        moloniExpeditionDocument = moloniExpeditionDocumentRepository.save(moloniExpeditionDocument);
        return moloniExpeditionDocumentMapper.toDto(moloniExpeditionDocument);
    }

    /**
     * Partially update a moloniExpeditionDocument.
     *
     * @param moloniExpeditionDocumentDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MoloniExpeditionDocumentDTO> partialUpdate(MoloniExpeditionDocumentDTO moloniExpeditionDocumentDTO) {
        log.debug("Request to partially update MoloniExpeditionDocument : {}", moloniExpeditionDocumentDTO);

        return moloniExpeditionDocumentRepository
            .findById(moloniExpeditionDocumentDTO.getId())
            .map(existingMoloniExpeditionDocument -> {
                moloniExpeditionDocumentMapper.partialUpdate(existingMoloniExpeditionDocument, moloniExpeditionDocumentDTO);

                return existingMoloniExpeditionDocument;
            })
            .map(moloniExpeditionDocumentRepository::save)
            .map(moloniExpeditionDocumentMapper::toDto);
    }

    /**
     * Get all the moloniExpeditionDocuments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MoloniExpeditionDocumentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MoloniExpeditionDocuments");
        return moloniExpeditionDocumentRepository.findAll(pageable).map(moloniExpeditionDocumentMapper::toDto);
    }

    /**
     * Get one moloniExpeditionDocument by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MoloniExpeditionDocumentDTO> findOne(Long id) {
        log.debug("Request to get MoloniExpeditionDocument : {}", id);
        return moloniExpeditionDocumentRepository.findById(id).map(moloniExpeditionDocumentMapper::toDto);
    }

    /**
     * Delete the moloniExpeditionDocument by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MoloniExpeditionDocument : {}", id);
        moloniExpeditionDocumentRepository.deleteById(id);
    }
}
