package com.library.catalog.author;

import com.library.catalog.author.dto.AuthorCreateRequest;
import com.library.catalog.author.dto.AuthorResponse;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface AuthorServices {
    AuthorResponse createAuthor(AuthorCreateRequest dto);
    AuthorResponse updateAuthor(long id, AuthorCreateRequest dto);
    void deleteAuthor(long id);
    AuthorResponse getAuthorById(long id);
    List<AuthorResponse> getAllAuthors();
    List<AuthorResponse> searchByName(String name);
}
