package biz.flowinn.product.logistics.domain;

import static org.assertj.core.api.Assertions.assertThat;

import biz.flowinn.product.logistics.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MoloniConfigurationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MoloniConfiguration.class);
        MoloniConfiguration moloniConfiguration1 = new MoloniConfiguration();
        moloniConfiguration1.setId(1L);
        MoloniConfiguration moloniConfiguration2 = new MoloniConfiguration();
        moloniConfiguration2.setId(moloniConfiguration1.getId());
        assertThat(moloniConfiguration1).isEqualTo(moloniConfiguration2);
        moloniConfiguration2.setId(2L);
        assertThat(moloniConfiguration1).isNotEqualTo(moloniConfiguration2);
        moloniConfiguration1.setId(null);
        assertThat(moloniConfiguration1).isNotEqualTo(moloniConfiguration2);
    }
}
