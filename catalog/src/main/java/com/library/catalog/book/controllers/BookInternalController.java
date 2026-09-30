package com.library.catalog.book.controllers;

import com.library.catalog.book.dto.ApiResponse;
import com.library.catalog.book.services.BookServiceImpl;
import com.library.catalog.book.dto.BookResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/books")
public class BookInternalController {
    @Autowired
    private BookServiceImpl bookService;

    @GetMapping("/{id}")
    public ApiResponse<BookResponse> getBookById(@PathVariable long id){
        return new ApiResponse<>(200, "success", bookService.getBookById(id));
    }

    @PutMapping("/{id}/reserve")
    public ApiResponse<BookResponse> reserveBook(@PathVariable long id){
        bookService.reserveBook(id);
        return new ApiResponse<>(200, "success", null);

    }

    @PutMapping("/{id}/release")
    public ApiResponse<BookResponse>  releaseBook(@PathVariable long id){
        bookService.releaseBook(id);
        return new ApiResponse<>(200, "success", null);

    }
}