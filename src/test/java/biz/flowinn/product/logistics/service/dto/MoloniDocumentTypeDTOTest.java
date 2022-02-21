package biz.flowinn.product.logistics.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import biz.flowinn.product.logistics.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MoloniDocumentTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MoloniDocumentTypeDTO.class);
        MoloniDocumentTypeDTO moloniDocumentTypeDTO1 = new MoloniDocumentTypeDTO();
        moloniDocumentTypeDTO1.setId(1L);
        MoloniDocumentTypeDTO moloniDocumentTypeDTO2 = new MoloniDocumentTypeDTO();
        assertThat(moloniDocumentTypeDTO1).isNotEqualTo(moloniDocumentTypeDTO2);
        moloniDocumentTypeDTO2.setId(moloniDocumentTypeDTO1.getId());
        assertThat(moloniDocumentTypeDTO1).isEqualTo(moloniDocumentTypeDTO2);
        moloniDocumentTypeDTO2.setId(2L);
        assertThat(moloniDocumentTypeDTO1).isNotEqualTo(moloniDocumentTypeDTO2);
        moloniDocumentTypeDTO1.setId(null);
        assertThat(moloniDocumentTypeDTO1).isNotEqualTo(moloniDocumentTypeDTO2);
    }
}
