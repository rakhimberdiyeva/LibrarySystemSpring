package com.library.loan.loan;

import com.library.loan.loan.dto.BookResponse;
import com.library.loan.loan.exceptions.BookNotAvailable;
import com.library.loan.loan.exceptions.BookNotFoundException;
import com.library.loan.loan.exceptions.CatalogUnavailable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
public class CatalogClient {

    private final RestClient client;

    public CatalogClient(RestClient catalogRestClient) {
        this.client = catalogRestClient;
    }

    public BookResponse getBook(Long bookId) {
        try {
            return client.get()
                    .uri("/internal/books/{id}", bookId)
                    .retrieve()
                    .body(BookResponse.class);
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