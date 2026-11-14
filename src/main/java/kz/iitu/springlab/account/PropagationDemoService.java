package kz.iitu.springlab.account;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PropagationDemoService {

    private final AuditService auditService;

    public PropagationDemoService(AuditService auditService) {
        this.auditService = auditService;
    }

    @Transactional
    public void demonstrateRequiredRollback() {
        auditService.logRequired("REQUIRED audit record");
        throw new RuntimeException("Rolling back outer transaction");
    }

    @Transactional
    public void demonstrateRequiresNewRollback() {
        auditService.logRequiresNew("REQUIRES_NEW audit record");
        throw new RuntimeException("Rolling back outer transaction");
    }
}