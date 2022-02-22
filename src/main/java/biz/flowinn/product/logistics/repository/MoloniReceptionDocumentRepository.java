package biz.flowinn.product.logistics.repository;

import biz.flowinn.product.logistics.domain.MoloniReceptionDocument;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MoloniReceptionDocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MoloniReceptionDocumentRepository extends JpaRepository<MoloniReceptionDocument, Long> {}
