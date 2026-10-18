
package kz.iitu.springlab.web;
import kz.iitu.springlab.web.dto.OnCreate;
import kz.iitu.springlab.web.dto.OnUpdate;
import jakarta.validation.groups.Default;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import kz.iitu.springlab.catalog.Book;
import kz.iitu.springlab.catalog.BookService;
import kz.iitu.springlab.catalog.BookStats;
import kz.iitu.springlab.web.dto.BookMapper;
import kz.iitu.springlab.web.dto.BookRequest;
import kz.iitu.springlab.web.dto.BookResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookRestController {

    private final BookService service;
    private final BookMapper mapper;

    public BookRestController(BookService service, BookMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<BookResponse> list(
            @RequestParam(required = false) String author,
            @RequestParam(defaultValue = "10") int limit
    ) {
        return mapper.toResponseList(
                service.findAll(author)
                        .stream()
                        .limit(Math.max(0, limit))
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> find(@PathVariable long id) {
        return ResponseEntity.ok(
                mapper.toResponse(service.getById(id))
        );
    }

    @PostMapping
    public ResponseEntity<BookResponse> create(
            @Validated({Default.class, OnCreate.class})
            @RequestBody BookRequest request
    ) {
        Book saved = service.create(mapper.toDomain(request));

        return ResponseEntity
                .created(URI.create("/api/books/" + saved.id()))
                .body(mapper.toResponse(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> replace(
            @PathVariable long id,
            @Validated({Default.class, OnUpdate.class})
            @RequestBody BookRequest request
    ) {
        if (!request.id().equals(id)) {
            return ResponseEntity.badRequest().build();
        }

        return service.replace(id, mapper.toDomain(request))
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
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
