package com.library.catalog.book;

import com.library.catalog.book.dto.BookCreateRequest;
import com.library.catalog.book.dto.BookResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {
    BookResponse createBook(BookCreateRequest dto);
    BookResponse updateBook(long id, BookCreateRequest dto);
    void deleteBook(long id);
    BookResponse getBookById(long id);
    List<BookResponse> getAllBooks();
    boolean hasAvailableCopies(long id);
    List<BookResponse> searchBook(String search);
}
