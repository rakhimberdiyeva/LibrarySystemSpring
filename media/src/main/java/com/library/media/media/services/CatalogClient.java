package com.library.media.media.services;

import com.library.media.media.dto.ApiResponse;
import com.library.media.media.dto.BookResponse;
import com.library.media.media.exceptions.BookNotFoundException;
import com.library.media.media.exceptions.CatalogUnavailable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
public class CatalogClient {

    private final RestClient client;

    private static final ParameterizedTypeReference<ApiResponse<BookResponse>> bookResponseType = new ParameterizedTypeReference<>() {};

    public CatalogClient(RestClient catalogRestClient) {
        this.client = catalogRestClient;
    }

    public BookResponse getBook(Long bookId) {
        try {
            ApiResponse<BookResponse> response = client.get()
                    .uri("/internal/books/{id}", bookId)
                    .retrieve()
                    .body(bookResponseType);
            if (response == null || response.getData() == null) {
                throw new BookNotFoundException("book not found: " + bookId);
            }
            return response.getData();
        } catch (HttpClientErrorException.NotFound e) {
            throw new BookNotFoundException("book not found: " + bookId);
        } catch (RestClientException e) {
            throw new CatalogUnavailable("Catalog service is unavailable" + e);
        }
    }

}