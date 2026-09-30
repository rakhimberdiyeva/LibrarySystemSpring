package com.library.loan.loan.services;

import com.library.loan.loan.dto.ApiResponse;
import com.library.loan.loan.dto.BookResponse;
import com.library.loan.loan.exceptions.BookNotAvailable;
import com.library.loan.loan.exceptions.BookNotFoundException;
import com.library.loan.loan.exceptions.CatalogUnavailable;
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

    public ApiResponse<BookResponse> getBook(Long bookId) {
        try {
            return client.get()
                    .uri("/internal/books/{id}", bookId)
                    .retrieve()
                    .body(bookResponseType);
        } catch (HttpClientErrorException.NotFound e) {
            throw new BookNotFoundException("book not found: " + bookId);
        } catch (RestClientException e) {
            throw new CatalogUnavailable("Catalog service is unavailable" + e);
        }
    }

    public void reserveBook(Long bookId) {
        try {
            client.put()
                    .uri("/internal/books/{id}/reserve", bookId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound e) {
            throw new BookNotFoundException("book not found: " + bookId);
        } catch (HttpClientErrorException.Conflict e) {
            throw new BookNotAvailable("book not available: " + bookId);
        } catch (RestClientException e) {
            throw new CatalogUnavailable("Catalog service is unavailable" + e);
        }
    }

    public void releaseBook(Long bookId) {
        try {
            client.put()
                    .uri("/internal/books/{id}/release", bookId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound e) {
            throw new BookNotFoundException("book not found: " + bookId);
        } catch (HttpClientErrorException.Conflict e) {
            throw new BookNotAvailable("book not available: " + bookId);
        } catch (RestClientException e) {
            throw new CatalogUnavailable("Catalog service is unavailable" + e);
        }
    }

}