package biz.flowinn.product.logistics.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MoloniConfiguration.
 */
@Entity
@Table(name = "moloni_configuration")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MoloniConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "company")
    private String company;

    @Column(name = "client")
    private String client;

    @Column(name = "secret")
    private String secret;

    @OneToMany(mappedBy = "config")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "config" }, allowSetters = true)
    private Set<MoloniReceptionDocument> receptions = new HashSet<>();

    @OneToMany(mappedBy = "config")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "shippingProcess", "config" }, allowSetters = true)
    private Set<MoloniExpeditionDocument> expeditions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MoloniConfiguration id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public MoloniConfiguration username(String username) {
        this.setUsername(username);
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public MoloniConfiguration password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompany() {
        return this.company;
    }

    public MoloniConfiguration company(String company) {
        this.setCompany(company);
        return this;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getClient() {
        return this.client;
    }

    public MoloniConfiguration client(String client) {
        this.setClient(client);
        return this;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getSecret() {
        return this.secret;
    }

    public MoloniConfiguration secret(String secret) {
        this.setSecret(secret);
        return this;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Set<MoloniReceptionDocument> getReceptions() {
        return this.receptions;
    }

    public void setReceptions(Set<MoloniReceptionDocument> moloniReceptionDocuments) {
        if (this.receptions != null) {
            this.receptions.forEach(i -> i.setConfig(null));
        }
        if (moloniReceptionDocuments != null) {
            moloniReceptionDocuments.forEach(i -> i.setConfig(this));
        }
        this.receptions = moloniReceptionDocuments;
    }

    public MoloniConfiguration receptions(Set<MoloniReceptionDocument> moloniReceptionDocuments) {
        this.setReceptions(moloniReceptionDocuments);
        return this;
    }

    public MoloniConfiguration addReceptions(MoloniReceptionDocument moloniReceptionDocument) {
        this.receptions.add(moloniReceptionDocument);
        moloniReceptionDocument.setConfig(this);
        return this;
    }

    public MoloniConfiguration removeReceptions(MoloniReceptionDocument moloniReceptionDocument) {
        this.receptions.remove(moloniReceptionDocument);
        moloniReceptionDocument.setConfig(null);
        return this;
    }

    public Set<MoloniExpeditionDocument> getExpeditions() {
        return this.expeditions;
    }

    public void setExpeditions(Set<MoloniExpeditionDocument> moloniExpeditionDocuments) {
        if (this.expeditions != null) {
            this.expeditions.forEach(i -> i.setConfig(null));
        }
        if (moloniExpeditionDocuments != null) {
            moloniExpeditionDocuments.forEach(i -> i.setConfig(this));
        }
        this.expeditions = moloniExpeditionDocuments;
    }

    public MoloniConfiguration expeditions(Set<MoloniExpeditionDocument> moloniExpeditionDocuments) {
        this.setExpeditions(moloniExpeditionDocuments);
        return this;
    }

    public MoloniConfiguration addExpeditions(MoloniExpeditionDocument moloniExpeditionDocument) {
        this.expeditions.add(moloniExpeditionDocument);
        moloniExpeditionDocument.setConfig(this);
        return this;
    }

    public MoloniConfiguration removeExpeditions(MoloniExpeditionDocument moloniExpeditionDocument) {
        this.expeditions.remove(moloniExpeditionDocument);
        moloniExpeditionDocument.setConfig(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MoloniConfiguration)) {
            return false;
        }
        return id != null && id.equals(((MoloniConfiguration) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MoloniConfiguration{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", company='" + getCompany() + "'" +
            ", client='" + getClient() + "'" +
            ", secret='" + getSecret() + "'" +
            "}";
    }
}
