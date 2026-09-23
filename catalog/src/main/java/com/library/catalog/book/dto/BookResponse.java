package com.library.catalog.book.dto;

import com.library.catalog.author.Author;
import com.library.catalog.author.dto.AuthorResponse;
import lombok.Data;

@Data
public class BookResponse {
    private long id;
    private String title;
    private String description;
    private int isbn;
    private int year;
    private int copiesTotal;
    private int copiesAvailable;
    private AuthorResponse author;
}
