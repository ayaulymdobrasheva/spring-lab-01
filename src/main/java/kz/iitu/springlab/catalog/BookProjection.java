package kz.iitu.springlab.catalog;

import kz.iitu.springlab.web.Genre;

public interface BookProjection {

    String getTitle();

    int getYear();

    Genre getGenre();
}