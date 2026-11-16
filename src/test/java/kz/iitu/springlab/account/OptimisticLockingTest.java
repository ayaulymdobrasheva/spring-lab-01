package kz.iitu.springlab.account;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import jakarta.persistence.OptimisticLockException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class OptimisticLockingTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Test
    void shouldDetectConcurrentUpdate() {
        TransactionTemplate transaction =
                new TransactionTemplate(transactionManager);

        Long accountId = transaction.execute(status ->
                entityManager.createQuery(
                                "select a.id from Account a where a.ownerName = :name",
                                Long.class)
                        .setParameter("name", "Alice")
                        .setMaxResults(1)
                        .getSingleResult()
        );

        // Transaction 1 loads the account
        Account first = transaction.execute(status ->
                entityManager.find(Account.class, accountId)
        );

        // Transaction 2 loads the same account version
        Account second = transaction.execute(status ->
                entityManager.find(Account.class, accountId)
        );

        // Transaction 1 updates and commits
        first.credit(new java.math.BigDecimal("10.00"));

        transaction.executeWithoutResult(status -> {
            entityManager.merge(first);
            entityManager.flush();
        });

        // Transaction 2 tries to update an outdated version
        second.credit(new java.math.BigDecimal("20.00"));
        assertThrows(
                OptimisticLockException.class,
                () -> transaction.executeWithoutResult(status -> {
                    entityManager.merge(second);
                    entityManager.flush();
                })
        );
    }
}