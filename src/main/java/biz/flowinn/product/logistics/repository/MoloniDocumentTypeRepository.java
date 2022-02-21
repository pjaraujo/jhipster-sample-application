package biz.flowinn.product.logistics.repository;

import biz.flowinn.product.logistics.domain.MoloniDocumentType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MoloniDocumentType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MoloniDocumentTypeRepository extends JpaRepository<MoloniDocumentType, Long> {}
