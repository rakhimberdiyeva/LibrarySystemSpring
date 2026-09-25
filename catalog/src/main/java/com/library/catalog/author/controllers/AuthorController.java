package com.library.catalog.author.controllers;

import com.library.catalog.author.AuthorServiceImpl;
import com.library.catalog.author.dto.AuthorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    @Autowired
    private AuthorServiceImpl authorService;

    @GetMapping("/{id}")
    public AuthorResponse getAuthorById(@PathVariable long id){
        return authorService.getAuthorById(id);
    }

    @GetMapping("/")
    public List<AuthorResponse> getAll(){
        return authorService.getAllAuthors();
    }

    @GetMapping("/search/{name}")
    public List<AuthorResponse> searchByName(@PathVariable String name){
        return authorService.searchByName(name);
    }

}
