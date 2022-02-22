package biz.flowinn.product.logistics.domain;

import biz.flowinn.product.logistics.domain.enumeration.DocumentType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MoloniDocumentType.
 */
@Entity
@Table(name = "moloni_document_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MoloniDocumentType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "document_type")
    private DocumentType documentType;

    @ManyToOne
    @JsonIgnoreProperties(value = { "receptions", "expeditions" }, allowSetters = true)
    private MoloniConfiguration config1;

    @ManyToOne
    @JsonIgnoreProperties(value = { "receptions", "expeditions" }, allowSetters = true)
    private MoloniConfiguration config2;

    @ManyToOne
    @JsonIgnoreProperties(value = { "receptions", "expeditions" }, allowSetters = true)
    private MoloniConfiguration config3;

    @ManyToOne
    @JsonIgnoreProperties(value = { "receptions", "expeditions" }, allowSetters = true)
    private MoloniConfiguration config4;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MoloniDocumentType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DocumentType getDocumentType() {
        return this.documentType;
    }

    public MoloniDocumentType documentType(DocumentType documentType) {
        this.setDocumentType(documentType);
        return this;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public MoloniConfiguration getConfig1() {
        return this.config1;
    }

    public void setConfig1(MoloniConfiguration moloniConfiguration) {
        this.config1 = moloniConfiguration;
    }

    public MoloniDocumentType config1(MoloniConfiguration moloniConfiguration) {
        this.setConfig1(moloniConfiguration);
        return this;
    }

    public MoloniConfiguration getConfig2() {
        return this.config2;
    }

    public void setConfig2(MoloniConfiguration moloniConfiguration) {
        this.config2 = moloniConfiguration;
    }

    public MoloniDocumentType config2(MoloniConfiguration moloniConfiguration) {
        this.setConfig2(moloniConfiguration);
        return this;
    }

    public MoloniConfiguration getConfig3() {
        return this.config3;
    }

    public void setConfig3(MoloniConfiguration moloniConfiguration) {
        this.config3 = moloniConfiguration;
    }

    public MoloniDocumentType config3(MoloniConfiguration moloniConfiguration) {
        this.setConfig3(moloniConfiguration);
        return this;
    }

    public MoloniConfiguration getConfig4() {
        return this.config4;
    }

    public void setConfig4(MoloniConfiguration moloniConfiguration) {
        this.config4 = moloniConfiguration;
    }

    public MoloniDocumentType config4(MoloniConfiguration moloniConfiguration) {
        this.setConfig4(moloniConfiguration);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MoloniDocumentType)) {
            return false;
        }
        return id != null && id.equals(((MoloniDocumentType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MoloniDocumentType{" +
            "id=" + getId() +
            ", documentType='" + getDocumentType() + "'" +
            "}";
    }
}
