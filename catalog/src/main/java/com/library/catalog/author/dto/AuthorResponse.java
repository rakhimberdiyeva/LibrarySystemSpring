package com.library.catalog.author.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AuthorResponse {
    private long id;
    private String fullname;
    private LocalDate birthDate;
}
