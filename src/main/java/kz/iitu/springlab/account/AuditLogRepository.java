package kz.iitu.springlab.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    long countByMessage(String message);

    @Transactional
    void deleteByMessage(String message);
}