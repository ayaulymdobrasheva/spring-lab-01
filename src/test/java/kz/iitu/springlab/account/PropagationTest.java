package kz.iitu.springlab.account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class PropagationTest {

    @Autowired
    private PropagationDemoService propagationDemoService;

    @Autowired
    private AuditLogRepository auditLogRepository;

    private static final String REQUIRED_MESSAGE = "REQUIRED audit record";
    private static final String REQUIRES_NEW_MESSAGE = "REQUIRES_NEW audit record";

    @BeforeEach
    void cleanAuditRecords() {
        auditLogRepository.deleteByMessage(REQUIRED_MESSAGE);
        auditLogRepository.deleteByMessage(REQUIRES_NEW_MESSAGE);
    }

    @Test
    void requiredShouldRollbackWithOuterTransaction() {
        assertThrows(RuntimeException.class,
                () -> propagationDemoService.demonstrateRequiredRollback());

        assertEquals(0,
                auditLogRepository.countByMessage(REQUIRED_MESSAGE));
    }

    @Test
    void requiresNewShouldCommitEvenIfOuterTransactionRollsBack() {
        assertThrows(RuntimeException.class,
                () -> propagationDemoService.demonstrateRequiresNewRollback());

        assertEquals(1,
                auditLogRepository.countByMessage(REQUIRES_NEW_MESSAGE));
    }
}