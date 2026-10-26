package kz.iitu.springlab.catalog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import kz.iitu.springlab.web.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByGenre(Genre genre);

    List<Book> findByTitleContainingIgnoreCase(String title);

    List<Book> findByYearBetweenOrderByYearDesc(
            int startYear,
            int endYear
    );

    List<Book> findTop3ByGenreOrderByYearDesc(Genre genre);

    long countByAuthorEntity_Name(String name);

    @Query("""
            SELECT b FROM Book b
            WHERE b.genre = :genre
            AND b.year >= :year
            """)
    List<Book> search(
            @Param("genre") Genre genre,
            @Param("year") int year
    );

    @Modifying
    @Query("""
            UPDATE Book b
            SET b.archived = true
            WHERE b.year < :year
            """)
    int archiveOlderThan(@Param("year") int year);
    @Query("""
    SELECT b.title AS title,
           b.year AS year,
           b.genre AS genre
    FROM Book b
    """)
    List<BookProjection> findAllBookProjections();
    // LAB 8: Pagination with author filtering
    Page<Book> findByAuthorNameContainingIgnoreCase(
            String authorName,
            Pageable pageable
    );
    @Query("SELECT b FROM Book b")
    Slice<Book> findAllAsSlice(Pageable pageable);
    // LAB 8: N+1 solution
    @Query("SELECT b FROM Book b JOIN FETCH b.authorEntity")
    List<Book> findAllWithAuthor();
}