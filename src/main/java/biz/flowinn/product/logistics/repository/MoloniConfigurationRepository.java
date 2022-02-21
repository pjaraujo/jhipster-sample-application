package biz.flowinn.product.logistics.repository;

import biz.flowinn.product.logistics.domain.MoloniConfiguration;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MoloniConfiguration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MoloniConfigurationRepository extends JpaRepository<MoloniConfiguration, Long> {}
