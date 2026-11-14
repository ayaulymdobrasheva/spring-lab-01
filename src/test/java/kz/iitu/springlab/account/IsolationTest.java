package kz.iitu.springlab.account;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class IsolationTest {

    @Autowired
    private IsolationDemoService isolationDemoService;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void shouldDemonstrateReadCommitted() throws Exception {
        testIsolation(true);
    }

    @Test
    void shouldDemonstrateRepeatableRead() throws Exception {
        testIsolation(false);
    }

    private void testIsolation(boolean readCommitted) throws Exception {
        Account account = accountRepository.findAll().stream()
                .filter(a -> "Alice".equals(a.getOwnerName()))
                .findFirst()
                .orElseThrow();

        Long accountId = account.getId();

        CountDownLatch firstReadDone = new CountDownLatch(1);
        CountDownLatch allowSecondRead = new CountDownLatch(1);

        ExecutorService executor = Executors.newSingleThreadExecutor();

        try {
            Future<List<BigDecimal>> future = executor.submit(() ->
                    readCommitted
                            ? isolationDemoService.readWithReadCommitted(
                            accountId, firstReadDone, allowSecondRead)
                            : isolationDemoService.readWithRepeatableRead(
                            accountId, firstReadDone, allowSecondRead)
            );

            assertEquals(true, firstReadDone.await(10, TimeUnit.SECONDS));

            isolationDemoService.increaseBalance(accountId, new BigDecimal("100.00"));

            allowSecondRead.countDown();

            List<BigDecimal> balances = future.get(15, TimeUnit.SECONDS);

            System.out.println("Isolation: " +
                    (readCommitted ? "READ_COMMITTED" : "REPEATABLE_READ"));
            System.out.println("First read: " + balances.get(0));
            System.out.println("Second read: " + balances.get(1));

            if (readCommitted) {
                assertEquals(0, balances.get(1).compareTo(
                        balances.get(0).add(new BigDecimal("100.00"))));
            } else {
                assertEquals(0, balances.get(1).compareTo(balances.get(0)));
            }
        } finally {
            allowSecondRead.countDown();
            executor.shutdownNow();
        }
    }
}