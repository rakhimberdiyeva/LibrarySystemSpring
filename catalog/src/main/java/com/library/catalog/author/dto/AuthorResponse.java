package com.library.catalog.author.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorResponse {
    private long id;
    private String fullname;
    private LocalDate birthDate;
}
