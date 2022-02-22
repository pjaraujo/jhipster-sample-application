package biz.flowinn.product.logistics.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShippingProcessMapperTest {

    private ShippingProcessMapper shippingProcessMapper;

    @BeforeEach
    public void setUp() {
        shippingProcessMapper = new ShippingProcessMapperImpl();
    }
}
