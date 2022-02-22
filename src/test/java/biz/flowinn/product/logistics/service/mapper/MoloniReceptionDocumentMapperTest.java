package biz.flowinn.product.logistics.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MoloniReceptionDocumentMapperTest {

    private MoloniReceptionDocumentMapper moloniReceptionDocumentMapper;

    @BeforeEach
    public void setUp() {
        moloniReceptionDocumentMapper = new MoloniReceptionDocumentMapperImpl();
    }
}
