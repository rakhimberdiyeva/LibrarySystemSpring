package com.library.catalog.book.dto;

import lombok.Data;

@Data
public class BookCreateRequest {
    private String title;
    private String description;
    private int isbn;
    private int year;
    private int copiesTotal;
    private int copiesAvailable;
    private long authorId;
}
