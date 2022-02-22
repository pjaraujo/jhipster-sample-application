package biz.flowinn.product.logistics.domain;

import static org.assertj.core.api.Assertions.assertThat;

import biz.flowinn.product.logistics.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ShippingProcessTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShippingProcess.class);
        ShippingProcess shippingProcess1 = new ShippingProcess();
        shippingProcess1.setId(1L);
        ShippingProcess shippingProcess2 = new ShippingProcess();
        shippingProcess2.setId(shippingProcess1.getId());
        assertThat(shippingProcess1).isEqualTo(shippingProcess2);
        shippingProcess2.setId(2L);
        assertThat(shippingProcess1).isNotEqualTo(shippingProcess2);
        shippingProcess1.setId(null);
        assertThat(shippingProcess1).isNotEqualTo(shippingProcess2);
    }
}
