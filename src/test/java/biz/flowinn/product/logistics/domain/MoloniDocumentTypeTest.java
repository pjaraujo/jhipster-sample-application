package biz.flowinn.product.logistics.domain;

import static org.assertj.core.api.Assertions.assertThat;

import biz.flowinn.product.logistics.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MoloniDocumentTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MoloniDocumentType.class);
        MoloniDocumentType moloniDocumentType1 = new MoloniDocumentType();
        moloniDocumentType1.setId(1L);
        MoloniDocumentType moloniDocumentType2 = new MoloniDocumentType();
        moloniDocumentType2.setId(moloniDocumentType1.getId());
        assertThat(moloniDocumentType1).isEqualTo(moloniDocumentType2);
        moloniDocumentType2.setId(2L);
        assertThat(moloniDocumentType1).isNotEqualTo(moloniDocumentType2);
        moloniDocumentType1.setId(null);
        assertThat(moloniDocumentType1).isNotEqualTo(moloniDocumentType2);
    }
}
