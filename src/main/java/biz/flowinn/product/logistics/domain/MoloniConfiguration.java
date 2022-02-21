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

    @OneToMany(mappedBy = "config1")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "config1", "config2", "config3", "config4" }, allowSetters = true)
    private Set<MoloniDocumentType> receptionIns = new HashSet<>();

    @OneToMany(mappedBy = "config2")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "config1", "config2", "config3", "config4" }, allowSetters = true)
    private Set<MoloniDocumentType> receptionOuts = new HashSet<>();

    @OneToMany(mappedBy = "config3")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "config1", "config2", "config3", "config4" }, allowSetters = true)
    private Set<MoloniDocumentType> expeditionIns = new HashSet<>();

    @OneToMany(mappedBy = "config4")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "config1", "config2", "config3", "config4" }, allowSetters = true)
    private Set<MoloniDocumentType> expeditionOuts = new HashSet<>();

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

    public Set<MoloniDocumentType> getReceptionIns() {
        return this.receptionIns;
    }

    public void setReceptionIns(Set<MoloniDocumentType> moloniDocumentTypes) {
        if (this.receptionIns != null) {
            this.receptionIns.forEach(i -> i.setConfig1(null));
        }
        if (moloniDocumentTypes != null) {
            moloniDocumentTypes.forEach(i -> i.setConfig1(this));
        }
        this.receptionIns = moloniDocumentTypes;
    }

    public MoloniConfiguration receptionIns(Set<MoloniDocumentType> moloniDocumentTypes) {
        this.setReceptionIns(moloniDocumentTypes);
        return this;
    }

    public MoloniConfiguration addReceptionIn(MoloniDocumentType moloniDocumentType) {
        this.receptionIns.add(moloniDocumentType);
        moloniDocumentType.setConfig1(this);
        return this;
    }

    public MoloniConfiguration removeReceptionIn(MoloniDocumentType moloniDocumentType) {
        this.receptionIns.remove(moloniDocumentType);
        moloniDocumentType.setConfig1(null);
        return this;
    }

    public Set<MoloniDocumentType> getReceptionOuts() {
        return this.receptionOuts;
    }

    public void setReceptionOuts(Set<MoloniDocumentType> moloniDocumentTypes) {
        if (this.receptionOuts != null) {
            this.receptionOuts.forEach(i -> i.setConfig2(null));
        }
        if (moloniDocumentTypes != null) {
            moloniDocumentTypes.forEach(i -> i.setConfig2(this));
        }
        this.receptionOuts = moloniDocumentTypes;
    }

    public MoloniConfiguration receptionOuts(Set<MoloniDocumentType> moloniDocumentTypes) {
        this.setReceptionOuts(moloniDocumentTypes);
        return this;
    }

    public MoloniConfiguration addReceptionOut(MoloniDocumentType moloniDocumentType) {
        this.receptionOuts.add(moloniDocumentType);
        moloniDocumentType.setConfig2(this);
        return this;
    }

    public MoloniConfiguration removeReceptionOut(MoloniDocumentType moloniDocumentType) {
        this.receptionOuts.remove(moloniDocumentType);
        moloniDocumentType.setConfig2(null);
        return this;
    }

    public Set<MoloniDocumentType> getExpeditionIns() {
        return this.expeditionIns;
    }

    public void setExpeditionIns(Set<MoloniDocumentType> moloniDocumentTypes) {
        if (this.expeditionIns != null) {
            this.expeditionIns.forEach(i -> i.setConfig3(null));
        }
        if (moloniDocumentTypes != null) {
            moloniDocumentTypes.forEach(i -> i.setConfig3(this));
        }
        this.expeditionIns = moloniDocumentTypes;
    }

    public MoloniConfiguration expeditionIns(Set<MoloniDocumentType> moloniDocumentTypes) {
        this.setExpeditionIns(moloniDocumentTypes);
        return this;
    }

    public MoloniConfiguration addExpeditionIn(MoloniDocumentType moloniDocumentType) {
        this.expeditionIns.add(moloniDocumentType);
        moloniDocumentType.setConfig3(this);
        return this;
    }

    public MoloniConfiguration removeExpeditionIn(MoloniDocumentType moloniDocumentType) {
        this.expeditionIns.remove(moloniDocumentType);
        moloniDocumentType.setConfig3(null);
        return this;
    }

    public Set<MoloniDocumentType> getExpeditionOuts() {
        return this.expeditionOuts;
    }

    public void setExpeditionOuts(Set<MoloniDocumentType> moloniDocumentTypes) {
        if (this.expeditionOuts != null) {
            this.expeditionOuts.forEach(i -> i.setConfig4(null));
        }
        if (moloniDocumentTypes != null) {
            moloniDocumentTypes.forEach(i -> i.setConfig4(this));
        }
        this.expeditionOuts = moloniDocumentTypes;
    }

    public MoloniConfiguration expeditionOuts(Set<MoloniDocumentType> moloniDocumentTypes) {
        this.setExpeditionOuts(moloniDocumentTypes);
        return this;
    }

    public MoloniConfiguration addExpeditionOut(MoloniDocumentType moloniDocumentType) {
        this.expeditionOuts.add(moloniDocumentType);
        moloniDocumentType.setConfig4(this);
        return this;
    }

    public MoloniConfiguration removeExpeditionOut(MoloniDocumentType moloniDocumentType) {
        this.expeditionOuts.remove(moloniDocumentType);
        moloniDocumentType.setConfig4(null);
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
