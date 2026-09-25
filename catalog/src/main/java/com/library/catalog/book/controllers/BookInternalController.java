package com.library.catalog.book.controllers;

import com.library.catalog.book.BookServiceImpl;
import com.library.catalog.book.dto.BookResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/books")
public class BookInternalController {
    @Autowired
    private BookServiceImpl bookService;

    @GetMapping("/{id}")
    public BookResponse getBookById(@PathVariable long id){
        return bookService.getBookById(id);
    }

    @PutMapping("/{id}/reserve")
    public void reserveBook(@PathVariable long id){
        System.out.println("controller");
         bookService.reserveBook(id);
    }

    @PutMapping("/{id}/release")
    public void releaseBook(@PathVariable long id){
         bookService.releaseBook(id);
    }
}