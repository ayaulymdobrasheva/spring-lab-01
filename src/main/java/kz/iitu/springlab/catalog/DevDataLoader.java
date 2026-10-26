package kz.iitu.springlab.catalog;
import kz.iitu.springlab.web.Genre;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Profile("dev")
@Order(1)
public class DevDataLoader implements CommandLineRunner {

    private final AuthorRepository authors;
    private final TagRepository tags;

    public DevDataLoader(AuthorRepository authors, TagRepository tags) {
        this.authors = authors;
        this.tags = tags;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (authors.count() > 0) {
            System.out.println("Demo data already exists. Skipping initialization.");
            return;
        }
        Tag classic    = tags.save(new Tag("classic"));
        Tag politics   = tags.save(new Tag("politics"));
        Tag philosophy = tags.save(new Tag("philosophy"));
        Tag dystopia   = tags.save(new Tag("dystopia"));

        Author tolstoy = new Author("Leo Tolstoy", "Russia");
        Author abai    = new Author("Abai Kunanbayuly", "Kazakhstan");
        Author orwell  = new Author("George Orwell", "UK");

        Book war = new Book("War and Peace", Genre.FICTION, 1869);
        war.addTag(classic);
        Book anna = new Book("Anna Karenina", Genre.FICTION, 1878);
        anna.addTag(classic);
        Book resurrection = new Book("Resurrection", Genre.FICTION, 1899);
        resurrection.addTag(classic);
        resurrection.addTag(philosophy);          // книга с двумя тегами
        tolstoy.addBook(war);
        tolstoy.addBook(anna);
        tolstoy.addBook(resurrection);            // у Толстого 3 книги

        Book words = new Book("Book of Words", Genre.HISTORY, 1904);
        words.addTag(philosophy);
        Book poems = new Book("Abai's Poems", Genre.FICTION, 1909);
        poems.addTag(classic);
        abai.addBook(words);
        abai.addBook(poems);

        Book book1984 = new Book("1984", Genre.FICTION, 1949);
        book1984.addTag(dystopia);
        book1984.addTag(politics);
        Book farm = new Book("Animal Farm", Genre.FICTION, 1945);
        farm.addTag(politics);
        Book catalonia = new Book("Homage to Catalonia", Genre.HISTORY, 1938);
        catalonia.addTag(politics);
        orwell.addBook(book1984);
        orwell.addBook(farm);
        orwell.addBook(catalonia);

        authors.save(tolstoy);   // cascade ALL сохранит и книги
        authors.save(abai);
        authors.save(orwell);
    }
}