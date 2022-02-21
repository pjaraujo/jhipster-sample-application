package biz.flowinn.product.logistics.service.mapper;

import biz.flowinn.product.logistics.domain.MoloniConfiguration;
import biz.flowinn.product.logistics.service.dto.MoloniConfigurationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MoloniConfiguration} and its DTO {@link MoloniConfigurationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MoloniConfigurationMapper extends EntityMapper<MoloniConfigurationDTO, MoloniConfiguration> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MoloniConfigurationDTO toDtoId(MoloniConfiguration moloniConfiguration);
}
