package com.library.catalog.book.controllers;

import com.library.catalog.book.dto.ApiResponse;
import com.library.catalog.book.services.BookServiceImpl;
import com.library.catalog.book.dto.BookCreateRequest;
import com.library.catalog.book.dto.BookResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/books")
public class BookAdminController {
    @Autowired
    private BookServiceImpl bookService;

    @PostMapping({"", "/"})
    public ApiResponse<BookResponse> createBook(@RequestBody BookCreateRequest request){
        return new ApiResponse<>(201, "success", bookService.createBook(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<BookResponse> updateBook(@RequestBody BookCreateRequest request, @PathVariable long id){
        return new ApiResponse<>(200, "success", bookService.updateBook(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteBook(@PathVariable long id){
        bookService.deleteBook(id);
        return new ApiResponse<>(200, "success", null);

    }

}
