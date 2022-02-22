package biz.flowinn.product.logistics.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import biz.flowinn.product.logistics.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MoloniReceptionDocumentDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MoloniReceptionDocumentDTO.class);
        MoloniReceptionDocumentDTO moloniReceptionDocumentDTO1 = new MoloniReceptionDocumentDTO();
        moloniReceptionDocumentDTO1.setId(1L);
        MoloniReceptionDocumentDTO moloniReceptionDocumentDTO2 = new MoloniReceptionDocumentDTO();
        assertThat(moloniReceptionDocumentDTO1).isNotEqualTo(moloniReceptionDocumentDTO2);
        moloniReceptionDocumentDTO2.setId(moloniReceptionDocumentDTO1.getId());
        assertThat(moloniReceptionDocumentDTO1).isEqualTo(moloniReceptionDocumentDTO2);
        moloniReceptionDocumentDTO2.setId(2L);
        assertThat(moloniReceptionDocumentDTO1).isNotEqualTo(moloniReceptionDocumentDTO2);
        moloniReceptionDocumentDTO1.setId(null);
        assertThat(moloniReceptionDocumentDTO1).isNotEqualTo(moloniReceptionDocumentDTO2);
    }
}
