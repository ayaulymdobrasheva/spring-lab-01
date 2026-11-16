package kz.iitu.springlab.catalog;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

@Component
@Profile("dev")
@Order(5)
public class OptimisticLockDemoRunner implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final TransactionTemplate transactionTemplate;

    public OptimisticLockDemoRunner(
            BookRepository bookRepository,
            TransactionTemplate transactionTemplate) {
        this.bookRepository = bookRepository;
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public void run(String... args) {

        System.out.println();
        System.out.println("======================================");
        System.out.println(" LAB 8 - OPTIMISTIC LOCKING TEST");
        System.out.println("======================================");

        Long bookId = bookRepository.findAll()
                .stream()
                .findFirst()
                .map(Book::getId)
                .orElse(null);

        if (bookId == null) {
            System.out.println("No books found.");
            return;
        }

        transactionTemplate.executeWithoutResult(status -> {

            Book book = bookRepository.findById(bookId)
                    .orElseThrow();

            System.out.println("Book: " + book.getTitle());
            System.out.println("Version before: " + book.getVersion());

            book.setYear(book.getYear() + 1);

            bookRepository.saveAndFlush(book);

            System.out.println("Version after: " + book.getVersion());
        });

        System.out.println("Optimistic Locking test completed.");
        System.out.println("======================================");
    }
}