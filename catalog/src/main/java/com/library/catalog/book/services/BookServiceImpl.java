package com.library.catalog.book.services;

import com.library.catalog.dto.PageResponse;
import com.library.catalog.book.dto.BookCreateRequest;
import com.library.catalog.book.dto.BookResponse;
import com.library.catalog.book.exceptions.BookCopiesConflict;
import com.library.catalog.book.exceptions.BookNotFound;
import com.library.catalog.book.mappers.BookMapper;
import com.library.catalog.book.models.Book;
import com.library.catalog.book.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    private final RedisTemplate<String, Object> redisTemplate;

    public BookServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public BookResponse createBook(BookCreateRequest dto) {
        if (dto.getCopiesAvailable() > dto.getCopiesTotal()){
            throw new BookCopiesConflict("available books can not be more than total books count");
        }
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
        String key = "book:" + id;
        redisTemplate.delete(key);
        return bookMapper.toDto(book);
    }

    @Override
    public void deleteBook(long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFound("book not found, id: " + id));
        bookRepository.delete(book);
        String key = "book:" + id;
        redisTemplate.delete(key);
    }

    @Override
    public BookResponse getBookById(long id) {
        String key = "book:" + id;
        Object cachedBook = redisTemplate.opsForValue().get(key);
        if (cachedBook != null){
            return (BookResponse) cachedBook;
        }
        BookResponse book = bookMapper.toDto(bookRepository.findById(id).orElseThrow(() -> new BookNotFound("book not found, id: " + id)));
        redisTemplate.opsForValue().set(key, book, 10, TimeUnit.MINUTES);
        return book;
    }


    @Override
    public PageResponse<BookResponse> getAllBooks(Pageable pageable) {
        String key = "books:page=%d:size=%d:sort=%s".formatted(
                pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        Object cachedBook = redisTemplate.opsForValue().get(key);
        if (cachedBook != null){
            return (PageResponse<BookResponse>) cachedBook;
        }

        PageResponse<BookResponse> books = PageResponse.from(
                bookRepository.findAll(pageable).map(bookMapper::toDto)
        );

        redisTemplate.opsForValue().set(key, books, 10, TimeUnit.MINUTES);
        return books;
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

    @Override
    @Transactional
    public void reserveBook(long id) {
        System.out.println("service");
        bookRepository.reserveBook(id);
    }

    @Override
    @Transactional
    public void releaseBook(long id) {
        bookRepository.releaseBook(id);
    }
}
