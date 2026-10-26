package kz.iitu.springlab.catalog;

public class DuplicateTitleException extends RuntimeException {

    public DuplicateTitleException(String title) {
        super("Book with title '" + title + "' already exists");
    }
}