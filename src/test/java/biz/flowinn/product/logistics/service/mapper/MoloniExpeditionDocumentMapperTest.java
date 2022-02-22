package biz.flowinn.product.logistics.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MoloniExpeditionDocumentMapperTest {

    private MoloniExpeditionDocumentMapper moloniExpeditionDocumentMapper;

    @BeforeEach
    public void setUp() {
        moloniExpeditionDocumentMapper = new MoloniExpeditionDocumentMapperImpl();
    }
}
