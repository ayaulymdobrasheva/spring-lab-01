
package kz.iitu.springlab.account;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountServiceTest {

    @Autowired
    private AccountService service;

    @Autowired
    private AccountRepository repository;

    @Test
    void shouldTransferMoneySuccessfully() {
        Account alice = repository.findAll().stream()
                .filter(a -> a.getOwnerName().equals("Alice"))
                .findFirst().orElseThrow();

        Account bob = repository.findAll().stream()
                .filter(a -> a.getOwnerName().equals("Bob"))
                .findFirst().orElseThrow();

        BigDecimal aliceBefore = alice.getBalance();
        BigDecimal bobBefore = bob.getBalance();

        service.transfer(
                alice.getId(),
                bob.getId(),
                new BigDecimal("100.00")
        );

        assertEquals(
                0,
                aliceBefore.subtract(new BigDecimal("100.00"))
                        .compareTo(repository.findById(alice.getId())
                                .orElseThrow().getBalance())
        );

        assertEquals(
                0,
                bobBefore.add(new BigDecimal("100.00"))
                        .compareTo(repository.findById(bob.getId())
                                .orElseThrow().getBalance())
        );
    }

    @Test
    void shouldRollbackAfterForcedFailure() {
        Account alice = repository.findAll().stream()
                .filter(a -> a.getOwnerName().equals("Alice"))
                .findFirst().orElseThrow();

        Account bob = repository.findAll().stream()
                .filter(a -> a.getOwnerName().equals("Bob"))
                .findFirst().orElseThrow();

        BigDecimal aliceBefore = alice.getBalance();
        BigDecimal bobBefore = bob.getBalance();

        assertThrows(RuntimeException.class, () ->
                service.transferWithForcedFailure(
                        alice.getId(),
                        bob.getId(),
                        new BigDecimal("100.00")
                )
        );

        assertEquals(0, aliceBefore.compareTo(
                repository.findById(alice.getId())
                        .orElseThrow().getBalance()
        ));

        assertEquals(0, bobBefore.compareTo(
                repository.findById(bob.getId())
                        .orElseThrow().getBalance()
        ));
    }

    @Test
    void shouldRejectTransferWithInsufficientFunds() {
        Account alice = repository.findAll().stream()
                .filter(a -> a.getOwnerName().equals("Alice"))
                .findFirst().orElseThrow();

        BigDecimal balanceBefore = alice.getBalance();

        assertThrows(IllegalStateException.class, () ->
                service.transfer(
                        alice.getId(),
                        alice.getId(),
                        balanceBefore.add(new BigDecimal("100.00"))
                )
        );

        assertEquals(0, balanceBefore.compareTo(
                repository.findById(alice.getId())
                        .orElseThrow().getBalance()
        ));
    }
    @Test
    void checkedExceptionShouldNotRollbackByDefault() {
        Account alice = repository.findAll().stream()
                .filter(a -> a.getOwnerName().equals("Alice"))
                .findFirst().orElseThrow();

        Account bob = repository.findAll().stream()
                .filter(a -> a.getOwnerName().equals("Bob"))
                .findFirst().orElseThrow();

        BigDecimal aliceBefore = alice.getBalance();
        BigDecimal bobBefore = bob.getBalance();

        assertThrows(Exception.class, () ->
                service.transferWithCheckedFailure(
                        alice.getId(),
                        bob.getId(),
                        new BigDecimal("10.00")
                )
        );

        assertEquals(0, aliceBefore.subtract(new BigDecimal("10.00"))
                .compareTo(repository.findById(alice.getId())
                        .orElseThrow().getBalance()));

        assertEquals(0, bobBefore.add(new BigDecimal("10.00"))
                .compareTo(repository.findById(bob.getId())
                        .orElseThrow().getBalance()));
    }

    @Test
    void checkedExceptionShouldRollbackWhenConfigured() {
        Account alice = repository.findAll().stream()
                .filter(a -> a.getOwnerName().equals("Alice"))
                .findFirst().orElseThrow();

        Account bob = repository.findAll().stream()
                .filter(a -> a.getOwnerName().equals("Bob"))
                .findFirst().orElseThrow();

        BigDecimal aliceBefore = alice.getBalance();
        BigDecimal bobBefore = bob.getBalance();

        assertThrows(Exception.class, () ->
                service.transferWithCheckedFailureRollback(
                        alice.getId(),
                        bob.getId(),
                        new BigDecimal("10.00")
                )
        );

        assertEquals(0, aliceBefore.compareTo(
                repository.findById(alice.getId())
                        .orElseThrow().getBalance()));

        assertEquals(0, bobBefore.compareTo(
                repository.findById(bob.getId())
                        .orElseThrow().getBalance()));
    }
}
