package com.library.catalog.book.controllers;

import com.library.catalog.book.BookServiceImpl;
import com.library.catalog.book.dto.BookCreateRequest;
import com.library.catalog.book.dto.BookResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/books")
public class BookAdminController {
    @Autowired
    private BookServiceImpl bookService;

    @PostMapping("/")
    public BookResponse createBook(@RequestBody BookCreateRequest request){
        return bookService.createBook(request);
    }

    @PutMapping("/{id}")
    public BookResponse updateBook(@RequestBody BookCreateRequest request, @PathVariable long id){
        return bookService.updateBook(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable long id){
        bookService.deleteBook(id);
    }

}
