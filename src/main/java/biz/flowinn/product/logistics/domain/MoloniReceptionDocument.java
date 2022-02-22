package biz.flowinn.product.logistics.domain;

import biz.flowinn.product.logistics.domain.enumeration.DocumentType;
import biz.flowinn.product.logistics.domain.enumeration.MoloniEndpoints;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MoloniReceptionDocument.
 */
@Entity
@Table(name = "moloni_reception_document")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MoloniReceptionDocument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "origin")
    private MoloniEndpoints origin;

    @Enumerated(EnumType.STRING)
    @Column(name = "destination")
    private MoloniEndpoints destination;

    @Enumerated(EnumType.STRING)
    @Column(name = "document_type")
    private DocumentType documentType;

    @ManyToOne
    @JsonIgnoreProperties(value = { "receptions", "expeditions" }, allowSetters = true)
    private MoloniConfiguration config;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MoloniReceptionDocument id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MoloniEndpoints getOrigin() {
        return this.origin;
    }

    public MoloniReceptionDocument origin(MoloniEndpoints origin) {
        this.setOrigin(origin);
        return this;
    }

    public void setOrigin(MoloniEndpoints origin) {
        this.origin = origin;
    }

    public MoloniEndpoints getDestination() {
        return this.destination;
    }

    public MoloniReceptionDocument destination(MoloniEndpoints destination) {
        this.setDestination(destination);
        return this;
    }

    public void setDestination(MoloniEndpoints destination) {
        this.destination = destination;
    }

    public DocumentType getDocumentType() {
        return this.documentType;
    }

    public MoloniReceptionDocument documentType(DocumentType documentType) {
        this.setDocumentType(documentType);
        return this;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public MoloniConfiguration getConfig() {
        return this.config;
    }

    public void setConfig(MoloniConfiguration moloniConfiguration) {
        this.config = moloniConfiguration;
    }

    public MoloniReceptionDocument config(MoloniConfiguration moloniConfiguration) {
        this.setConfig(moloniConfiguration);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MoloniReceptionDocument)) {
            return false;
        }
        return id != null && id.equals(((MoloniReceptionDocument) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MoloniReceptionDocument{" +
            "id=" + getId() +
            ", origin='" + getOrigin() + "'" +
            ", destination='" + getDestination() + "'" +
            ", documentType='" + getDocumentType() + "'" +
            "}";
    }
}
