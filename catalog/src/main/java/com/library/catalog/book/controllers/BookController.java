package com.library.catalog.book.controllers;

import com.library.catalog.book.BookServiceImpl;
import com.library.catalog.book.dto.BookCreateRequest;
import com.library.catalog.book.dto.BookResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookServiceImpl bookService;

    @GetMapping("/{id}")
    public BookResponse getBookById(@PathVariable long id){
        return bookService.getBookById(id);
    }

    @GetMapping("/")
    public List<BookResponse> getAll(){
        return bookService.getAllBooks();
    }

    @GetMapping("/search/{name}")
    public List<BookResponse> getBookById(@PathVariable String name){
        return bookService.searchBook(name);
    }
}
