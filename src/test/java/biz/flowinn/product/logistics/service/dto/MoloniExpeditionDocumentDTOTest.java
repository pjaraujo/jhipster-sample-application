package biz.flowinn.product.logistics.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import biz.flowinn.product.logistics.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MoloniExpeditionDocumentDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MoloniExpeditionDocumentDTO.class);
        MoloniExpeditionDocumentDTO moloniExpeditionDocumentDTO1 = new MoloniExpeditionDocumentDTO();
        moloniExpeditionDocumentDTO1.setId(1L);
        MoloniExpeditionDocumentDTO moloniExpeditionDocumentDTO2 = new MoloniExpeditionDocumentDTO();
        assertThat(moloniExpeditionDocumentDTO1).isNotEqualTo(moloniExpeditionDocumentDTO2);
        moloniExpeditionDocumentDTO2.setId(moloniExpeditionDocumentDTO1.getId());
        assertThat(moloniExpeditionDocumentDTO1).isEqualTo(moloniExpeditionDocumentDTO2);
        moloniExpeditionDocumentDTO2.setId(2L);
        assertThat(moloniExpeditionDocumentDTO1).isNotEqualTo(moloniExpeditionDocumentDTO2);
        moloniExpeditionDocumentDTO1.setId(null);
        assertThat(moloniExpeditionDocumentDTO1).isNotEqualTo(moloniExpeditionDocumentDTO2);
    }
}
