package com.library.catalog.author;

import com.library.catalog.author.dto.AuthorCreateRequest;
import com.library.catalog.author.dto.AuthorResponse;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {
    public Author toEntity(AuthorCreateRequest dto){
        Author author = new Author();
        author.setFullname(dto.getFullname());
        author.setBirthDate(dto.getBirthDate());
        return author;
    }

    public AuthorResponse toDto(Author author){
        AuthorResponse dto = new AuthorResponse();
        dto.setId(author.getId());
        dto.setFullname(author.getFullname());
        dto.setBirthDate(author.getBirthDate());
        return dto;
    }
}
