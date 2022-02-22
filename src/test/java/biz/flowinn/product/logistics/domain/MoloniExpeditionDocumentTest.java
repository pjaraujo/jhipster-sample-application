package biz.flowinn.product.logistics.domain;

import static org.assertj.core.api.Assertions.assertThat;

import biz.flowinn.product.logistics.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MoloniExpeditionDocumentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MoloniExpeditionDocument.class);
        MoloniExpeditionDocument moloniExpeditionDocument1 = new MoloniExpeditionDocument();
        moloniExpeditionDocument1.setId(1L);
        MoloniExpeditionDocument moloniExpeditionDocument2 = new MoloniExpeditionDocument();
        moloniExpeditionDocument2.setId(moloniExpeditionDocument1.getId());
        assertThat(moloniExpeditionDocument1).isEqualTo(moloniExpeditionDocument2);
        moloniExpeditionDocument2.setId(2L);
        assertThat(moloniExpeditionDocument1).isNotEqualTo(moloniExpeditionDocument2);
        moloniExpeditionDocument1.setId(null);
        assertThat(moloniExpeditionDocument1).isNotEqualTo(moloniExpeditionDocument2);
    }
}
