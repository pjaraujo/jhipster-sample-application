package biz.flowinn.product.logistics.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link biz.flowinn.product.logistics.domain.ShippingProcess} entity.
 */
public class ShippingProcessDTO implements Serializable {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShippingProcessDTO)) {
            return false;
        }

        ShippingProcessDTO shippingProcessDTO = (ShippingProcessDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, shippingProcessDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShippingProcessDTO{" +
            "id=" + getId() +
            "}";
    }
}
