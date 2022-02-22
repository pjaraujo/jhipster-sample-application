package biz.flowinn.product.logistics.service.dto;

import biz.flowinn.product.logistics.domain.enumeration.MoloniEndpoints;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link biz.flowinn.product.logistics.domain.MoloniExpeditionDocument} entity.
 */
public class MoloniExpeditionDocumentDTO implements Serializable {

    private Long id;

    private MoloniEndpoints origin;

    private MoloniEndpoints destination;

    private ShippingProcessDTO shippingProcess;

    private MoloniConfigurationDTO config;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MoloniEndpoints getOrigin() {
        return origin;
    }

    public void setOrigin(MoloniEndpoints origin) {
        this.origin = origin;
    }

    public MoloniEndpoints getDestination() {
        return destination;
    }

    public void setDestination(MoloniEndpoints destination) {
        this.destination = destination;
    }

    public ShippingProcessDTO getShippingProcess() {
        return shippingProcess;
    }

    public void setShippingProcess(ShippingProcessDTO shippingProcess) {
        this.shippingProcess = shippingProcess;
    }

    public MoloniConfigurationDTO getConfig() {
        return config;
    }

    public void setConfig(MoloniConfigurationDTO config) {
        this.config = config;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MoloniExpeditionDocumentDTO)) {
            return false;
        }

        MoloniExpeditionDocumentDTO moloniExpeditionDocumentDTO = (MoloniExpeditionDocumentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, moloniExpeditionDocumentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MoloniExpeditionDocumentDTO{" +
            "id=" + getId() +
            ", origin='" + getOrigin() + "'" +
            ", destination='" + getDestination() + "'" +
            ", shippingProcess=" + getShippingProcess() +
            ", config=" + getConfig() +
            "}";
    }
}
