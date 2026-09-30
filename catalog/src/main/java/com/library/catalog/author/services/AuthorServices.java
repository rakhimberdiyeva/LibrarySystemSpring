package com.library.catalog.author.services;

import com.library.catalog.dto.PageResponse;
import com.library.catalog.author.dto.AuthorCreateRequest;
import com.library.catalog.author.dto.AuthorResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface AuthorServices {
    AuthorResponse createAuthor(AuthorCreateRequest dto);
    AuthorResponse updateAuthor(long id, AuthorCreateRequest dto);
    void deleteAuthor(long id);
    AuthorResponse getAuthorById(long id);
    PageResponse<AuthorResponse> getAllAuthors(Pageable pageable);
    List<AuthorResponse> searchByName(String name);
}
