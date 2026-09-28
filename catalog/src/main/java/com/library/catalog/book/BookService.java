package com.library.catalog.book;

import com.library.catalog.PageResponse;
import com.library.catalog.book.dto.BookCreateRequest;
import com.library.catalog.book.dto.BookResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {
    BookResponse createBook(BookCreateRequest dto);
    BookResponse updateBook(long id, BookCreateRequest dto);
    void deleteBook(long id);
    BookResponse getBookById(long id);
    PageResponse<BookResponse> getAllBooks(Pageable pageable);
    boolean hasAvailableCopies(long id);
    List<BookResponse> searchBook(String search);
    void reserveBook(long id);
    void releaseBook(long id);
}
