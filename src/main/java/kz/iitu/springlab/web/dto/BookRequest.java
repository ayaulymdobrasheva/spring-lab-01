package kz.iitu.springlab.web.dto;

import kz.iitu.springlab.web.Genre;
import jakarta.validation.constraints.*;

public record BookRequest(

        @Null(groups = OnCreate.class)
        @NotNull(groups = OnUpdate.class)
        Long id,

        @NotBlank
        @Size(max = 120)
        String title,

        @NotBlank
        String author,

        @Min(1450)
        @Max(2100)
        int year,

        @NotNull
        Genre genre
) {
}