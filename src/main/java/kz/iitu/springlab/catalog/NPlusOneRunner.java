package kz.iitu.springlab.catalog;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Profile("dev")
@Order(3)
public class NPlusOneRunner implements CommandLineRunner {

    private final BookRepository repository;

    public NPlusOneRunner(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public void run(String... args) {

        // BEFORE: without JOIN FETCH
        System.out.println();
        System.out.println("===== N+1: START =====");

        List<Book> books = repository.findAll();

        for (Book book : books) {
            System.out.println(
                    book.getTitle() + " / " +
                            book.getAuthor().getName()
            );
        }

        System.out.println("===== N+1: END =====");

        // AFTER: with JOIN FETCH
        System.out.println();
        System.out.println("===== JOIN FETCH: START =====");

        List<Book> booksWithAuthors =
                repository.findAllWithAuthor();

        for (Book book : booksWithAuthors) {
            System.out.println(
                    book.getTitle() + " / " +
                            book.getAuthor().getName()
            );
        }

        System.out.println("===== JOIN FETCH: END =====");
    }
}