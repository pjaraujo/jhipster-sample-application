package biz.flowinn.product.logistics.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import biz.flowinn.product.logistics.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MoloniConfigurationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MoloniConfigurationDTO.class);
        MoloniConfigurationDTO moloniConfigurationDTO1 = new MoloniConfigurationDTO();
        moloniConfigurationDTO1.setId(1L);
        MoloniConfigurationDTO moloniConfigurationDTO2 = new MoloniConfigurationDTO();
        assertThat(moloniConfigurationDTO1).isNotEqualTo(moloniConfigurationDTO2);
        moloniConfigurationDTO2.setId(moloniConfigurationDTO1.getId());
        assertThat(moloniConfigurationDTO1).isEqualTo(moloniConfigurationDTO2);
        moloniConfigurationDTO2.setId(2L);
        assertThat(moloniConfigurationDTO1).isNotEqualTo(moloniConfigurationDTO2);
        moloniConfigurationDTO1.setId(null);
        assertThat(moloniConfigurationDTO1).isNotEqualTo(moloniConfigurationDTO2);
    }
}
