package com.library.catalog.book;

import com.library.catalog.book.dto.BookCreateRequest;
import com.library.catalog.book.dto.BookResponse;
import com.library.catalog.book.exceptions.BookNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService{
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    @Override
    public BookResponse createBook(BookCreateRequest dto) {
        Book book = bookRepository.save(bookMapper.toEntity(dto));
        return bookMapper.toDto(book);
    }

    @Override
    public BookResponse updateBook(long id, BookCreateRequest dto) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFound("book not found, id: " + id));
        if (dto.getTitle() != null){
            book.setTitle(dto.getTitle());
        }
        if (dto.getDescription() != null){
            book.setDescription(dto.getDescription());
        }
        if (dto.getIsbn() != 0){
            book.setIsbn(dto.getIsbn());
        }
        if (dto.getYear() != 0){
            book.setYear(dto.getYear());
        }
        return bookMapper.toDto(book);
    }

    @Override
    public void deleteBook(long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFound("book not found, id: " + id));
        bookRepository.delete(book);
    }

    @Override
    public BookResponse getBookById(long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFound("book not found, id: " + id));
        return bookMapper.toDto(book);
    }

    @Override
    public List<BookResponse> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(b -> bookMapper.toDto(b)).toList();
    }

    @Override
    public boolean hasAvailableCopies(long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFound("book not found, id: " + id));
        return book.getCopiesAvailable() > 0;
    }

    @Override
    public List<BookResponse> searchBook(String search) {
        List<Book> books = bookRepository.searchByTitleOrDesc(search);
        return books.stream().map(b -> bookMapper.toDto(b)).toList();
    }
}
