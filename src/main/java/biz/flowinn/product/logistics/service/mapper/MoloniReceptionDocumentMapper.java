package biz.flowinn.product.logistics.service.mapper;

import biz.flowinn.product.logistics.domain.MoloniReceptionDocument;
import biz.flowinn.product.logistics.service.dto.MoloniReceptionDocumentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MoloniReceptionDocument} and its DTO {@link MoloniReceptionDocumentDTO}.
 */
@Mapper(componentModel = "spring", uses = { MoloniConfigurationMapper.class })
public interface MoloniReceptionDocumentMapper extends EntityMapper<MoloniReceptionDocumentDTO, MoloniReceptionDocument> {
    @Mapping(target = "config", source = "config", qualifiedByName = "id")
    MoloniReceptionDocumentDTO toDto(MoloniReceptionDocument s);
}
