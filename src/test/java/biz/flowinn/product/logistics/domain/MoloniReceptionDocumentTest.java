package biz.flowinn.product.logistics.domain;

import static org.assertj.core.api.Assertions.assertThat;

import biz.flowinn.product.logistics.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MoloniReceptionDocumentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MoloniReceptionDocument.class);
        MoloniReceptionDocument moloniReceptionDocument1 = new MoloniReceptionDocument();
        moloniReceptionDocument1.setId(1L);
        MoloniReceptionDocument moloniReceptionDocument2 = new MoloniReceptionDocument();
        moloniReceptionDocument2.setId(moloniReceptionDocument1.getId());
        assertThat(moloniReceptionDocument1).isEqualTo(moloniReceptionDocument2);
        moloniReceptionDocument2.setId(2L);
        assertThat(moloniReceptionDocument1).isNotEqualTo(moloniReceptionDocument2);
        moloniReceptionDocument1.setId(null);
        assertThat(moloniReceptionDocument1).isNotEqualTo(moloniReceptionDocument2);
    }
}
