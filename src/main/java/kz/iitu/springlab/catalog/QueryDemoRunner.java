package kz.iitu.springlab.catalog;
import kz.iitu.springlab.web.Genre;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Profile("dev")
@Order(2)
public class QueryDemoRunner implements CommandLineRunner {

    private final BookRepository repo;

    public QueryDemoRunner(BookRepository repo) { this.repo = repo; }

    @Override
    @Transactional
    public void run(String... args) {
        System.out.println("##### findByGenre");
        System.out.println(repo.findByGenre(Genre.FICTION).size());

        System.out.println("##### findByTitleContainingIgnoreCase");
        System.out.println(repo.findByTitleContainingIgnoreCase("war").size());

        System.out.println("##### findByYearBetweenOrderByYearDesc");
        System.out.println(repo.findByYearBetweenOrderByYearDesc(1900, 1950).size());

        System.out.println("##### findTop3ByGenreOrderByYearDesc");
        System.out.println(repo.findTop3ByGenreOrderByYearDesc(Genre.FICTION));

        System.out.println("##### countByAuthorName");
        System.out.println(repo.countByAuthorEntity_Name("George Orwell"));

        System.out.println("##### search (JPQL)");
        System.out.println(repo.search(Genre.FICTION, 1900));

        System.out.println("##### archiveOlderThan (bulk)");
        System.out.println("updated rows: " + repo.archiveOlderThan(1900));
        System.out.println("##### END");
    }
}