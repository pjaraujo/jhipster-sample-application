package biz.flowinn.product.logistics.service.mapper;

import biz.flowinn.product.logistics.domain.MoloniExpeditionDocument;
import biz.flowinn.product.logistics.service.dto.MoloniExpeditionDocumentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MoloniExpeditionDocument} and its DTO {@link MoloniExpeditionDocumentDTO}.
 */
@Mapper(componentModel = "spring", uses = { ShippingProcessMapper.class, MoloniConfigurationMapper.class })
public interface MoloniExpeditionDocumentMapper extends EntityMapper<MoloniExpeditionDocumentDTO, MoloniExpeditionDocument> {
    @Mapping(target = "shippingProcess", source = "shippingProcess", qualifiedByName = "id")
    @Mapping(target = "config", source = "config", qualifiedByName = "id")
    MoloniExpeditionDocumentDTO toDto(MoloniExpeditionDocument s);
}
