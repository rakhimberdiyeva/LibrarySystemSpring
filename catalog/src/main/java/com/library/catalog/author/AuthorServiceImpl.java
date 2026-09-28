package com.library.catalog.author;

import com.library.catalog.PageResponse;
import com.library.catalog.author.dto.AuthorCreateRequest;
import com.library.catalog.author.dto.AuthorResponse;
import com.library.catalog.author.exceptions.AuthorNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Service
public class AuthorServiceImpl implements AuthorServices{
    @Autowired
    private AuthorMapper authorMapper;

    @Autowired
    private AuthorRepository authorRepository;

    private final RedisTemplate<String, Object> redisTemplate;

    public AuthorServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


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
        String key = "author:" + id;
        redisTemplate.delete(key);
        return authorMapper.toDto(author);
    }

    @Override
    public void deleteAuthor(long id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new AuthorNotFound("author not found, id: " + id));
        authorRepository.deleteById(id);
        String key = "author:" + id;
        redisTemplate.delete(key);
    }

    @Override
    public AuthorResponse getAuthorById(long id) {
        String key = "author:" + id;
        Object cachedAuthor = redisTemplate.opsForValue().get(key);
        if (cachedAuthor != null){
            return (AuthorResponse) cachedAuthor;
        }
        AuthorResponse author = authorMapper.toDto(authorRepository.findById(id).orElseThrow(() -> new AuthorNotFound("author not found, id: " + id)));
        redisTemplate.opsForValue().set(key, author, 10, TimeUnit.MINUTES);
        return author;
    }

//    @Override
//    public List<AuthorResponse> getAllAuthors() {
//        String key = "authors:";
//        List<AuthorResponse> cachedAuthors = (List<AuthorResponse>) redisTemplate.opsForValue().get(key);
//        if (cachedAuthors != null){
//            return cachedAuthors;
//        }
//        List<AuthorResponse> authors = authorRepository.findAll().stream()
//                .map(authorMapper::toDto)
//                .collect(Collectors.toCollection(ArrayList::new));
//        redisTemplate.opsForValue().set(key, authors, 10, TimeUnit.MINUTES);
//        return authors;
//    }

    @Override
    public PageResponse<AuthorResponse> getAllAuthors(Pageable pageable) {
        String key = "authors:page=%d:size=%d:sort=%s".formatted(
                pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());

        Object cached = redisTemplate.opsForValue().get(key);
        if (cached != null) {
            return (PageResponse<AuthorResponse>) cached;
        }

        PageResponse<AuthorResponse> authors = PageResponse.from(
                authorRepository.findAll(pageable).map(authorMapper::toDto)
        );

        redisTemplate.opsForValue().set(key, authors, 10, TimeUnit.MINUTES);
        return authors;
    }

    @Override
    public List<AuthorResponse> searchByName(String name) {
        List<Author> authors = authorRepository.searchByName(name);
        return authors.stream().map(a -> authorMapper.toDto(a)).toList();
    }
}
