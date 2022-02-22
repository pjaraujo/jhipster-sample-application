package biz.flowinn.product.logistics.repository;

import biz.flowinn.product.logistics.domain.ShippingProcess;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ShippingProcess entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShippingProcessRepository extends JpaRepository<ShippingProcess, Long> {}
