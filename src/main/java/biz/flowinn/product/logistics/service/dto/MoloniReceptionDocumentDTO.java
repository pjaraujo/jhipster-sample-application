package biz.flowinn.product.logistics.service.dto;

import biz.flowinn.product.logistics.domain.enumeration.DocumentType;
import biz.flowinn.product.logistics.domain.enumeration.MoloniEndpoints;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link biz.flowinn.product.logistics.domain.MoloniReceptionDocument} entity.
 */
public class MoloniReceptionDocumentDTO implements Serializable {

    private Long id;

    private MoloniEndpoints origin;

    private MoloniEndpoints destination;

    private DocumentType documentType;

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

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
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
        if (!(o instanceof MoloniReceptionDocumentDTO)) {
            return false;
        }

        MoloniReceptionDocumentDTO moloniReceptionDocumentDTO = (MoloniReceptionDocumentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, moloniReceptionDocumentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MoloniReceptionDocumentDTO{" +
            "id=" + getId() +
            ", origin='" + getOrigin() + "'" +
            ", destination='" + getDestination() + "'" +
            ", documentType='" + getDocumentType() + "'" +
            ", config=" + getConfig() +
            "}";
    }
}
