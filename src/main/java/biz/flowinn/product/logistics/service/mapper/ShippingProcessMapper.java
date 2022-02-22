package biz.flowinn.product.logistics.service.mapper;

import biz.flowinn.product.logistics.domain.ShippingProcess;
import biz.flowinn.product.logistics.service.dto.ShippingProcessDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ShippingProcess} and its DTO {@link ShippingProcessDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ShippingProcessMapper extends EntityMapper<ShippingProcessDTO, ShippingProcess> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ShippingProcessDTO toDtoId(ShippingProcess shippingProcess);
}
