package biz.flowinn.product.logistics.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import biz.flowinn.product.logistics.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ShippingProcessDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShippingProcessDTO.class);
        ShippingProcessDTO shippingProcessDTO1 = new ShippingProcessDTO();
        shippingProcessDTO1.setId(1L);
        ShippingProcessDTO shippingProcessDTO2 = new ShippingProcessDTO();
        assertThat(shippingProcessDTO1).isNotEqualTo(shippingProcessDTO2);
        shippingProcessDTO2.setId(shippingProcessDTO1.getId());
        assertThat(shippingProcessDTO1).isEqualTo(shippingProcessDTO2);
        shippingProcessDTO2.setId(2L);
        assertThat(shippingProcessDTO1).isNotEqualTo(shippingProcessDTO2);
        shippingProcessDTO1.setId(null);
        assertThat(shippingProcessDTO1).isNotEqualTo(shippingProcessDTO2);
    }
}
