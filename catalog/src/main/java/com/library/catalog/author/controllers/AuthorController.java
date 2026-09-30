package com.library.catalog.author.controllers;

import com.library.catalog.author.dto.ApiResponse;
import com.library.catalog.dto.PageResponse;
import com.library.catalog.author.services.AuthorServiceImpl;
import com.library.catalog.author.dto.AuthorResponse;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    @Autowired
    private AuthorServiceImpl authorService;

    @GetMapping("/{id}")
    public ApiResponse<AuthorResponse> getAuthorById(@PathVariable long id){
        return new ApiResponse<>(200, "success", authorService.getAuthorById(id));
    }

    @GetMapping("/")
    public ApiResponse<PageResponse<AuthorResponse>> getAll(@ParameterObject @PageableDefault(size = 20, sort = "id") Pageable pageable){
        return new ApiResponse<>(200, "success", authorService.getAllAuthors(pageable));
    }

    @GetMapping("/search/{name}")
    public ApiResponse<List<AuthorResponse>> searchByName(@PathVariable String name){
        return new ApiResponse<>(200, "success", authorService.searchByName(name));
    }

}
