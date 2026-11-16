
package kz.iitu.springlab.account;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AccountService {

    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void transfer(Long fromId, Long toId, BigDecimal amount) {

        Account from = repository.findById(fromId)
                .orElseThrow(() -> new IllegalArgumentException("Source account not found"));

        Account to = repository.findById(toId)
                .orElseThrow(() -> new IllegalArgumentException("Target account not found"));

        from.debit(amount);
        to.credit(amount);

        repository.save(from);
        repository.save(to);
    }

    @Transactional
    public void transferWithForcedFailure(
            Long fromId,
            Long toId,
            BigDecimal amount) {

        Account from = repository.findById(fromId)
                .orElseThrow(() -> new IllegalArgumentException("Source account not found"));

        Account to = repository.findById(toId)
                .orElseThrow(() -> new IllegalArgumentException("Target account not found"));

        from.debit(amount);

        // Simulate an unexpected error
        throw new RuntimeException("Simulated transaction failure");
    }
    @Transactional
    public void transferWithCheckedFailure(
            Long fromId,
            Long toId,
            BigDecimal amount) throws Exception {

        Account from = repository.findById(fromId)
                .orElseThrow(() -> new IllegalArgumentException("Source account not found"));

        Account to = repository.findById(toId)
                .orElseThrow(() -> new IllegalArgumentException("Target account not found"));

        from.debit(amount);
        to.credit(amount);

        throw new Exception("Simulated checked exception");
    }

    @Transactional(rollbackFor = Exception.class)
    public void transferWithCheckedFailureRollback(
            Long fromId,
            Long toId,
            BigDecimal amount) throws Exception {

        Account from = repository.findById(fromId)
                .orElseThrow(() -> new IllegalArgumentException("Source account not found"));

        Account to = repository.findById(toId)
                .orElseThrow(() -> new IllegalArgumentException("Target account not found"));

        from.debit(amount);
        to.credit(amount);

        throw new Exception("Simulated checked exception with rollback");
    }
}
