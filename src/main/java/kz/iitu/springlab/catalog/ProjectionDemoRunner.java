package kz.iitu.springlab.catalog;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("dev")
@Order(4)
public class ProjectionDemoRunner implements CommandLineRunner {

    private final BookRepository bookRepository;

    public ProjectionDemoRunner(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) {

        System.out.println();
        System.out.println("======================================");
        System.out.println(" LAB 8 - VARIANT 5: PROJECTION");
        System.out.println("======================================");

        List<BookProjection> books =
                bookRepository.findAllBookProjections();

        for (BookProjection book : books) {
            System.out.println("Title: " + book.getTitle()
                    + " | Year: " + book.getYear()
                    + " | Genre: " + book.getGenre());
        }

        System.out.println("--------------------------------------");
        System.out.println("Total books: " + books.size());
        System.out.println("======================================");
    }
}