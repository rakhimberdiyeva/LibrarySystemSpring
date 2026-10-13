package com.library.catalog.book.controllers;

import com.library.catalog.book.dto.ApiResponse;
import com.library.catalog.dto.PageResponse;
import com.library.catalog.book.services.BookServiceImpl;
import com.library.catalog.book.dto.BookResponse;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookServiceImpl bookService;

    @GetMapping("/{id}")
    public ApiResponse<BookResponse> getBookById(@PathVariable long id){
        return new ApiResponse<>(200, "success", bookService.getBookById(id));
    }

    @GetMapping({"", "/"})
    public ApiResponse<PageResponse<BookResponse>> getAll(@ParameterObject @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return new ApiResponse<>(200, "success", bookService.getAllBooks(pageable));
    }

    @GetMapping("/search/{name}")
    public ApiResponse<List<BookResponse>> getBookById(@PathVariable String name){
        return new ApiResponse<>(200, "success", bookService.searchBook(name));}
}
