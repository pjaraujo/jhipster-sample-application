package biz.flowinn.product.logistics.domain;

import biz.flowinn.product.logistics.domain.enumeration.MoloniEndpoints;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MoloniExpeditionDocument.
 */
@Entity
@Table(name = "moloni_expedition_document")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MoloniExpeditionDocument implements Serializable {

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

    @ManyToOne
    private ShippingProcess shippingProcess;

    @ManyToOne
    @JsonIgnoreProperties(value = { "receptions", "expeditions" }, allowSetters = true)
    private MoloniConfiguration config;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MoloniExpeditionDocument id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MoloniEndpoints getOrigin() {
        return this.origin;
    }

    public MoloniExpeditionDocument origin(MoloniEndpoints origin) {
        this.setOrigin(origin);
        return this;
    }

    public void setOrigin(MoloniEndpoints origin) {
        this.origin = origin;
    }

    public MoloniEndpoints getDestination() {
        return this.destination;
    }

    public MoloniExpeditionDocument destination(MoloniEndpoints destination) {
        this.setDestination(destination);
        return this;
    }

    public void setDestination(MoloniEndpoints destination) {
        this.destination = destination;
    }

    public ShippingProcess getShippingProcess() {
        return this.shippingProcess;
    }

    public void setShippingProcess(ShippingProcess shippingProcess) {
        this.shippingProcess = shippingProcess;
    }

    public MoloniExpeditionDocument shippingProcess(ShippingProcess shippingProcess) {
        this.setShippingProcess(shippingProcess);
        return this;
    }

    public MoloniConfiguration getConfig() {
        return this.config;
    }

    public void setConfig(MoloniConfiguration moloniConfiguration) {
        this.config = moloniConfiguration;
    }

    public MoloniExpeditionDocument config(MoloniConfiguration moloniConfiguration) {
        this.setConfig(moloniConfiguration);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MoloniExpeditionDocument)) {
            return false;
        }
        return id != null && id.equals(((MoloniExpeditionDocument) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MoloniExpeditionDocument{" +
            "id=" + getId() +
            ", origin='" + getOrigin() + "'" +
            ", destination='" + getDestination() + "'" +
            "}";
    }
}
