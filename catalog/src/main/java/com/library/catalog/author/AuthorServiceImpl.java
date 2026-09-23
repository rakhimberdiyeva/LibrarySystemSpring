package com.library.catalog.author;

import com.library.catalog.author.dto.AuthorCreateRequest;
import com.library.catalog.author.dto.AuthorResponse;
import com.library.catalog.author.exceptions.AuthorNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AuthorServiceImpl implements AuthorServices{
    @Autowired
    private AuthorMapper authorMapper;

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public AuthorResponse createAuthor(AuthorCreateRequest dto) {
        Author author = authorRepository.save(authorMapper.toEntity(dto));
        return authorMapper.toDto(author);
    }

    @Override
    public AuthorResponse updateAuthor(long id, AuthorCreateRequest dto) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new AuthorNotFound("author not found, id: " + id));
        if (dto.getFullname() != null){
            author.setFullname(dto.getFullname());
        }
        if (dto.getFullname() != null){
            author.setBirthDate(dto.getBirthDate());
        }
        return authorMapper.toDto(author);
    }

    @Override
    public void deleteAuthor(long id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new AuthorNotFound("author not found, id: " + id));
        authorRepository.deleteById(id);
    }

    @Override
    public AuthorResponse getAuthorById(long id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new AuthorNotFound("author not found, id: " + id));
        return authorMapper.toDto(author);
    }

    @Override
    public List<AuthorResponse> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream().map(a -> authorMapper.toDto(a)).toList();
    }

    @Override
    public List<AuthorResponse> searchByName(String name) {
        List<Author> authors = authorRepository.searchByName(name);
        return authors.stream().map(a -> authorMapper.toDto(a)).toList();
    }
}
