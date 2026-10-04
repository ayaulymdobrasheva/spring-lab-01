package kz.iitu.springlab.web;
import kz.iitu.springlab.catalog.BookStats;
import kz.iitu.springlab.catalog.Book;
import kz.iitu.springlab.catalog.BookService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookRestController {

    private final BookService service;

    public BookRestController(BookService service) {
        this.service = service;
    }


    // GET /api/books
    // GET /api/books?author=Bloch&limit=1
    @GetMapping
    public List<Book> list(
            @RequestParam(required = false) String author,
            @RequestParam(defaultValue = "10") int limit
    ) {
        return service.findAll(author)
                .stream()
                .limit(limit)
                .toList();
    }


    // GET /api/books/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Book> find(
            @PathVariable long id
    ) {

        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );
    }


    // POST /api/books
    @PostMapping
    public ResponseEntity<Book> create(
            @RequestBody Book book
    ) {

        Book saved = service.create(book);

        return ResponseEntity
                .created(
                        URI.create("/api/books/" + saved.id())
                )
                .body(saved);
    }


    // PUT /api/books/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Book> replace(
            @PathVariable long id,
            @RequestBody Book book
    ) {

        return service.replace(id, book)
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );
    }


    // DELETE /api/books/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable long id
    ) {

        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
    @GetMapping("/stats")
    public ResponseEntity<BookStats> stats() {
        return ResponseEntity.ok(service.getStats());
    }
}