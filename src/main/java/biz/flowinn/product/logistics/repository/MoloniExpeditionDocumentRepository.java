package biz.flowinn.product.logistics.repository;

import biz.flowinn.product.logistics.domain.MoloniExpeditionDocument;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MoloniExpeditionDocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MoloniExpeditionDocumentRepository extends JpaRepository<MoloniExpeditionDocument, Long> {}
