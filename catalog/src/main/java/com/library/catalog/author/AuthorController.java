package com.library.catalog.author;

import com.library.catalog.author.dto.AuthorCreateRequest;
import com.library.catalog.author.dto.AuthorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {
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
