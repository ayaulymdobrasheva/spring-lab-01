package kz.iitu.springlab.catalog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository repository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository repository,
                       AuthorRepository authorRepository) {
        this.repository = repository;
        this.authorRepository = authorRepository;
    }

    @Transactional(readOnly = true)
    public List<Book> findAll(String author) {
        List<Book> books = repository.findAll();

        if (author == null || author.isBlank()) {
            return books;
        }

        String search = author.toLowerCase();

        return books.stream()
                .filter(book ->
                        book.author() != null &&
                                book.author().toLowerCase().contains(search))
                .toList();
    }

    // LAB 8: Pagination and Sorting
    @Transactional(readOnly = true)
    public Page<Book> findAll(String author, Pageable pageable) {

        if (author == null || author.isBlank()) {
            return repository.findAll(pageable);
        }

        return repository.findByAuthorNameContainingIgnoreCase(
                author, pageable
        );
    }

    @Transactional(readOnly = true)
    public Optional<Book> findById(long id) {
        return repository.findById(id);
    }

    @Transactional(readOnly = true)
    public Book getById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    @Transactional
    public Book create(Book book) {

        boolean exists = repository.findAll().stream()
                .anyMatch(existing ->
                        existing.title().equalsIgnoreCase(book.title()));

        if (exists) {
            throw new DuplicateTitleException(book.title());
        }

        Book newBook = new Book(
                null,
                book.title(),
                book.author(),
                book.year(),
                book.genre(),
                book.createdAt()
        );

        assignAuthor(newBook, book.author());

        return repository.save(newBook);
    }

    @Transactional
    public Optional<Book> replace(long id, Book book) {

        Optional<Book> existing = repository.findById(id);

        if (existing.isEmpty()) {
            return Optional.empty();
        }

        Book current = existing.get();

        current.setTitle(book.title());
        current.setYear(book.year());
        assignAuthor(current, book.author());

        return Optional.of(repository.save(current));
    }

    @Transactional
    public boolean delete(long id) {

        if (!repository.existsById(id)) {
            return false;
        }

        repository.deleteById(id);
        return true;
    }

    @Transactional(readOnly = true)
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

    private void assignAuthor(Book book, String authorName) {

        if (authorName == null || authorName.isBlank()) {
            return;
        }

        Author author = authorRepository.findAll()
                .stream()
                .filter(a -> a.getName().equalsIgnoreCase(authorName))
                .findFirst()
                .orElseGet(() ->
                        authorRepository.save(
                                new Author(authorName, null)
                        )
                );

        book.setAuthor(author);
    }
}