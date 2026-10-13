package kz.iitu.springlab.catalog;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> findAll(String author) {

        List<Book> books = repository.findAll();

        if (author == null || author.isBlank()) {
            return books;
        }

        String search = author.toLowerCase();

        return books.stream()
                .filter(book ->
                        book.author().toLowerCase().contains(search))
                .toList();
    }

    public Optional<Book> findById(long id) {
        return repository.findById(id);
    }
    public Book getById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }
    public Book create(Book book) {
        return repository.save(
                new Book(
                        null,
                        book.title(),
                        book.author(),
                        book.year()
                )
        );
    }

    public Optional<Book> replace(long id, Book book) {

        if (repository.findById(id).isEmpty()) {
            return Optional.empty();
        }

        Book updated = new Book(
                id,
                book.title(),
                book.author(),
                book.year()
        );

        return Optional.of(repository.save(updated));
    }

    public boolean delete(long id) {
        return repository.deleteById(id);
    }
    public BookStats getStats() {
        List<Book> books = repository.findAll();

        if (books.isEmpty()) {
            return new BookStats(0, null, null);
        }

        int earliestYear = books.stream()
                .mapToInt(Book::year)
                .min()
                .orElse(0);

        int latestYear = books.stream()
                .mapToInt(Book::year)
                .max()
                .orElse(0);

        return new BookStats(
                books.size(),
                earliestYear,
                latestYear
        );
    }
}