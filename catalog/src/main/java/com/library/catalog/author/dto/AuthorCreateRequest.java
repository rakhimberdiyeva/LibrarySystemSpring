package com.library.catalog.author.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AuthorCreateRequest {
    private String fullname;
    private LocalDate birthDate;
}
