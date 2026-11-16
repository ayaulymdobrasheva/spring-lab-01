package kz.iitu.springlab.account;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
public class IsolationDemoService {

    private final EntityManager entityManager;

    public IsolationDemoService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<BigDecimal> readWithReadCommitted(
            Long accountId,
            CountDownLatch firstReadDone,
            CountDownLatch allowSecondRead) {

        return readTwice(accountId, firstReadDone, allowSecondRead);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public List<BigDecimal> readWithRepeatableRead(
            Long accountId,
            CountDownLatch firstReadDone,
            CountDownLatch allowSecondRead) {

        return readTwice(accountId, firstReadDone, allowSecondRead);
    }

    private List<BigDecimal> readTwice(
            Long accountId,
            CountDownLatch firstReadDone,
            CountDownLatch allowSecondRead) {

        Account firstAccount = entityManager.find(Account.class, accountId);
        BigDecimal firstBalance = firstAccount.getBalance();

        firstReadDone.countDown();

        try {
            if (!allowSecondRead.await(10, TimeUnit.SECONDS)) {
                throw new IllegalStateException("Timed out waiting for the second transaction");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Transaction interrupted", e);
        }

        entityManager.clear();

        Account secondAccount = entityManager.find(Account.class, accountId);
        BigDecimal secondBalance = secondAccount.getBalance();

        return List.of(firstBalance, secondBalance);
    }
    @Transactional(propagation = org.springframework.transaction.annotation.Propagation.REQUIRES_NEW)
    public void increaseBalance(Long accountId, BigDecimal amount) {
        Account account = entityManager.find(Account.class, accountId);
        account.credit(amount);
        entityManager.flush();
    }
}