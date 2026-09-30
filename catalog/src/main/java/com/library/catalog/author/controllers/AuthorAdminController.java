package com.library.catalog.author.controllers;

import com.library.catalog.author.dto.ApiResponse;
import com.library.catalog.author.services.AuthorServiceImpl;
import com.library.catalog.author.dto.AuthorCreateRequest;
import com.library.catalog.author.dto.AuthorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin/authors")
public class AuthorAdminController {
    @Autowired
    private AuthorServiceImpl authorService;

    @PostMapping("/")
    public ApiResponse<AuthorResponse> createAuthor(@RequestBody AuthorCreateRequest request){
        return new ApiResponse<>(201, "success", authorService.createAuthor(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<AuthorResponse> updateAuthor(@RequestBody AuthorCreateRequest request, @PathVariable long id){
        return new ApiResponse<>(200, "success", authorService.updateAuthor(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse  deleteAuthor(@PathVariable long id){
        authorService.deleteAuthor(id);
        return new ApiResponse<>(204, "success", null);
    }

}
