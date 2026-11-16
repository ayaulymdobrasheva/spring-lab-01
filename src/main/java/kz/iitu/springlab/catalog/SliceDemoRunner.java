package kz.iitu.springlab.catalog;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Profile("dev")
@Order(6)
public class SliceDemoRunner implements CommandLineRunner {

    private final BookRepository bookRepository;

    public SliceDemoRunner(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public void run(String... args) {

        System.out.println();
        System.out.println("======================================");
        System.out.println(" LAB 8 - SLICE TEST");
        System.out.println("======================================");

        var pageable = PageRequest.of(
                1,
                3,
                Sort.by(Sort.Direction.DESC, "year")
        );

        Slice<Book> slice =
                bookRepository.findAllAsSlice(pageable);

        System.out.println("Page number: " + slice.getNumber());
        System.out.println("Page size: " + slice.getSize());
        System.out.println("Has next page: " + slice.hasNext());

        for (Book book : slice.getContent()) {
            System.out.println(
                    book.getTitle() + " | " + book.getYear()
            );
        }

        System.out.println("Number of books: "
                + slice.getNumberOfElements());

        System.out.println("======================================");
    }
}