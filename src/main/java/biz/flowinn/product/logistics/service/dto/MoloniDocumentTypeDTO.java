package biz.flowinn.product.logistics.service.dto;

import biz.flowinn.product.logistics.domain.enumeration.DocumentType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link biz.flowinn.product.logistics.domain.MoloniDocumentType} entity.
 */
public class MoloniDocumentTypeDTO implements Serializable {

    private Long id;

    private DocumentType documentType;

    private MoloniConfigurationDTO config1;

    private MoloniConfigurationDTO config2;

    private MoloniConfigurationDTO config3;

    private MoloniConfigurationDTO config4;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public MoloniConfigurationDTO getConfig1() {
        return config1;
    }

    public void setConfig1(MoloniConfigurationDTO config1) {
        this.config1 = config1;
    }

    public MoloniConfigurationDTO getConfig2() {
        return config2;
    }

    public void setConfig2(MoloniConfigurationDTO config2) {
        this.config2 = config2;
    }

    public MoloniConfigurationDTO getConfig3() {
        return config3;
    }

    public void setConfig3(MoloniConfigurationDTO config3) {
        this.config3 = config3;
    }

    public MoloniConfigurationDTO getConfig4() {
        return config4;
    }

    public void setConfig4(MoloniConfigurationDTO config4) {
        this.config4 = config4;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MoloniDocumentTypeDTO)) {
            return false;
        }

        MoloniDocumentTypeDTO moloniDocumentTypeDTO = (MoloniDocumentTypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, moloniDocumentTypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MoloniDocumentTypeDTO{" +
            "id=" + getId() +
            ", documentType='" + getDocumentType() + "'" +
            ", config1=" + getConfig1() +
            ", config2=" + getConfig2() +
            ", config3=" + getConfig3() +
            ", config4=" + getConfig4() +
            "}";
    }
}
