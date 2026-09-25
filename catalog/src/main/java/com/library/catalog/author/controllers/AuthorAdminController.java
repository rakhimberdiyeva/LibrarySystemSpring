package com.library.catalog.author.controllers;

import com.library.catalog.author.AuthorServiceImpl;
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
    public AuthorResponse createAuthor(@RequestBody AuthorCreateRequest request){
        return authorService.createAuthor(request);
    }

    @PutMapping("/{id}")
    public AuthorResponse updateAuthor(@RequestBody AuthorCreateRequest request, @PathVariable long id){
        return authorService.updateAuthor(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable long id){
        authorService.deleteAuthor(id);
    }

}
