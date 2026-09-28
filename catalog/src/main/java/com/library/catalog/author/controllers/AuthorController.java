package com.library.catalog.author.controllers;

import com.library.catalog.PageResponse;
import com.library.catalog.author.AuthorServiceImpl;
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
    public AuthorResponse getAuthorById(@PathVariable long id){
        return authorService.getAuthorById(id);
    }

    @GetMapping("/")
    public PageResponse<AuthorResponse> getAll(@ParameterObject @PageableDefault(size = 20, sort = "id") Pageable pageable){
        return authorService.getAllAuthors(pageable);
    }

    @GetMapping("/search/{name}")
    public List<AuthorResponse> searchByName(@PathVariable String name){
        return authorService.searchByName(name);
    }

}
