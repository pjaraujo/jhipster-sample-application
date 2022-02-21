package biz.flowinn.product.logistics.service.mapper;

import biz.flowinn.product.logistics.domain.MoloniDocumentType;
import biz.flowinn.product.logistics.service.dto.MoloniDocumentTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MoloniDocumentType} and its DTO {@link MoloniDocumentTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = { MoloniConfigurationMapper.class })
public interface MoloniDocumentTypeMapper extends EntityMapper<MoloniDocumentTypeDTO, MoloniDocumentType> {
    @Mapping(target = "config1", source = "config1", qualifiedByName = "id")
    @Mapping(target = "config2", source = "config2", qualifiedByName = "id")
    @Mapping(target = "config3", source = "config3", qualifiedByName = "id")
    @Mapping(target = "config4", source = "config4", qualifiedByName = "id")
    MoloniDocumentTypeDTO toDto(MoloniDocumentType s);
}
