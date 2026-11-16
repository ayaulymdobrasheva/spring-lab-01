package kz.iitu.springlab.catalog;

import jakarta.persistence.*;
import kz.iitu.springlab.repository.AppUser;
import kz.iitu.springlab.web.Genre;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    // LAB 10, VARIANT 5: Book owner
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private AppUser owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author authorEntity;

    @Column(name = "author_name")
    private String authorName;

    private int year;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    private Instant createdAt;

    @ManyToMany
    @JoinTable(
            name = "book_tag",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    private boolean archived = false;

    @Version
    private long version;

    protected Book() {
    }

    // Constructor for previous labs
    public Book(Long id, String title, String author,
                int year, Genre genre, Instant createdAt) {
        this.id = id;
        this.title = title;
        this.authorName = author;
        this.year = year;
        this.genre = genre;
        this.createdAt = createdAt;
    }

    // Constructor for Lab 8
    public Book(String title, Genre genre, int year) {
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.createdAt = Instant.now();
    }

    // LAB 10: Owner getter and setter
    public AppUser getOwner() {
        return owner;
    }

    public void setOwner(AppUser owner) {
        this.owner = owner;
    }

    public Long getOwnerId() {
        return owner != null ? owner.getId() : null;
    }

    // Author methods
    public void setAuthor(Author author) {
        this.authorEntity = author;

        if (author != null) {
            this.authorName = author.getName();
        }
    }

    public Author getAuthor() {
        return authorEntity;
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getVersion() {
        return version;
    }

    public Genre getGenre() {
        return genre;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    // Compatibility with previous record Book
    public Long id() {
        return id;
    }

    public String title() {
        return title;
    }

    public String author() {
        return authorName;
    }

    public int year() {
        return year;
    }

    public Genre genre() {
        return genre;
    }

    public Instant createdAt() {
        return createdAt;
    }
}