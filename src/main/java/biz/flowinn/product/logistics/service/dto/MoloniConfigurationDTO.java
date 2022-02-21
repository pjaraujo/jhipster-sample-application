package biz.flowinn.product.logistics.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link biz.flowinn.product.logistics.domain.MoloniConfiguration} entity.
 */
public class MoloniConfigurationDTO implements Serializable {

    private Long id;

    private String username;

    private String password;

    private String company;

    private String client;

    private String secret;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MoloniConfigurationDTO)) {
            return false;
        }

        MoloniConfigurationDTO moloniConfigurationDTO = (MoloniConfigurationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, moloniConfigurationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MoloniConfigurationDTO{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", company='" + getCompany() + "'" +
            ", client='" + getClient() + "'" +
            ", secret='" + getSecret() + "'" +
            "}";
    }
}
