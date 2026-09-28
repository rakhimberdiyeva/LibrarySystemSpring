package com.library.catalog.author.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class AuthorCreateRequest {
    private String fullname;
    private LocalDate birthDate;
}
