package com.library.catalog.authors;

import com.library.catalog.dto.PageResponse;
import com.library.catalog.author.models.Author;
import com.library.catalog.author.mappers.AuthorMapper;
import com.library.catalog.author.repository.AuthorRepository;
import com.library.catalog.author.services.AuthorServiceImpl;
import com.library.catalog.author.dto.AuthorCreateRequest;
import com.library.catalog.author.dto.AuthorResponse;
import com.library.catalog.author.exceptions.AuthorNotFound;
import com.library.catalog.book.models.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTests {
    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorMapper authorMapper;

    @Mock
    private  RedisTemplate<String, Object> redisTemplate;

    @InjectMocks
    private AuthorServiceImpl authorService;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    private AuthorCreateRequest authorCreate;
    private AuthorResponse authorResponse;
    private Author author;

    @BeforeEach
    void beforeEach(){
        List<Book> books = new ArrayList<>();
        authorCreate = new AuthorCreateRequest("user1", LocalDate.of(1999, 3, 3));
        authorResponse = new AuthorResponse(1L, "user1", LocalDate.of(1999, 3, 3));
        author = new Author(1L, "user1", LocalDate.of(1999, 3, 3), books);
    }

    @Test
    @DisplayName("create author")
    void createAuthor() {
        Author newAuthor = new Author("user1", LocalDate.of(1999, 3, 3), new ArrayList<>());

        when(authorMapper.toEntity(authorCreate)).thenReturn(newAuthor);
        when(authorRepository.save(newAuthor)).thenReturn(author);
        when(authorMapper.toDto(author)).thenReturn(authorResponse);

        AuthorResponse result  = authorService.createAuthor(authorCreate);

        assertThat(result).isEqualTo(authorResponse);
        verify(authorRepository).save(newAuthor);
    }

    @Test
    @DisplayName("update author both fields")
    void updateAuthor() {
        AuthorResponse expected = new AuthorResponse(1L, "user2", LocalDate.of(2000, 3, 3));
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(authorRepository.save(any(Author.class))).thenAnswer(returnsFirstArg());
        when(authorMapper.toDto(author)).thenReturn(expected);

        AuthorCreateRequest updateRequest = new AuthorCreateRequest("user2", LocalDate.of(2000, 3, 3));
        AuthorResponse result = authorService.updateAuthor(1L, updateRequest);

        assertThat(result).isEqualTo(expected);
        assertThat(author.getFullname()).isEqualTo("user2");
        assertThat(author.getBirthDate()).isEqualTo(LocalDate.of(2000, 3, 3));
        verify(redisTemplate).delete("author:1");
    }

    @Test
    @DisplayName("update author only fullname")
    void updateAuthorFullname() {
        AuthorResponse expected = new AuthorResponse(1L, "user2", LocalDate.of(1999, 3, 3));
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(authorRepository.save(any(Author.class))).thenAnswer(returnsFirstArg());
        when(authorMapper.toDto(author)).thenReturn(expected);

        AuthorCreateRequest updateRequest = new AuthorCreateRequest("user2", null);
        AuthorResponse result = authorService.updateAuthor(1L, updateRequest);

        assertThat(result).isEqualTo(expected);
        assertThat(author.getFullname()).isEqualTo("user2");
        verify(redisTemplate).delete("author:1");
    }

    @Test
    @DisplayName("update author both null")
    void updateAuthorNull() {
        AuthorResponse expected = new AuthorResponse(1L, "user1", LocalDate.of(1999, 3, 3));
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(authorRepository.save(any(Author.class))).thenAnswer(returnsFirstArg());
        when(authorMapper.toDto(author)).thenReturn(expected);

        AuthorCreateRequest updateRequest = new AuthorCreateRequest(null, null);
        AuthorResponse result = authorService.updateAuthor(1L, updateRequest);

        assertThat(result).isEqualTo(expected);
        assertThat(author.getFullname()).isEqualTo("user1");
        verify(redisTemplate).delete("author:1");
    }

    @Test
    @DisplayName("delete author")
    void deleteAuthor() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        authorService.deleteAuthor(1L);

        verify(authorRepository).deleteById(1L);
        verify(redisTemplate).delete("author:1");
    }

    @Test
    @DisplayName("delete author exception")
    void deleteAuthorException() {
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AuthorNotFound.class, () -> authorService.deleteAuthor(1L));

        verify(authorRepository, never()).deleteById(anyLong());
        verify(redisTemplate, never()).delete(anyString());
    }

    @Test
    @DisplayName("get author by id")
    void getById() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get("author:1")).thenReturn(null);
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(authorMapper.toDto(author)).thenReturn(authorResponse);

        AuthorResponse result = authorService.getAuthorById(1L);

        assertThat(result).isEqualTo(authorResponse);
        assertThat(result.getFullname()).isEqualTo("user1");
        verify(valueOperations).set("author:1", authorResponse, 10, TimeUnit.MINUTES);
    }


    @Test
    @DisplayName("get author by id cached")
    void getByIdCached() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get("author:1")).thenReturn(authorResponse);

        AuthorResponse result = authorService.getAuthorById(1L);

        assertThat(result).isEqualTo(authorResponse);
        assertThat(result.getFullname()).isEqualTo("user1");
        verifyNoInteractions(authorRepository);
    }

    @Test
    @DisplayName("get author by id exception")
    void getByIdException() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get("author:1")).thenReturn(null);
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AuthorNotFound.class, () -> authorService.getAuthorById(1L));
    }

    @Test
    @DisplayName("get all authors")
    void getAllAuthors() {
        List<AuthorResponse> authors = new ArrayList<>();
        authors.add(authorResponse);

        Pageable pageable = PageRequest.of(0, 10);
        String key = "authors:page=0:size=10:sort=id";
        Page<Author> page = new PageImpl<>(List.of(author), pageable, 1);

        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(authorRepository.findAll(pageable)).thenReturn(page);
        when(authorMapper.toDto(author)).thenReturn(authorResponse);

        PageResponse<AuthorResponse> result = authorService.getAllAuthors(pageable);

        assertThat(result.content()).isEqualTo(authors);
        assertThat(result.totalElements()).isEqualTo(1);
    }

    @Test
    @DisplayName("get all authors cached")
    void getAllAuthorsCached() {
        Pageable pageable = PageRequest.of(0, 10);
        String key = "authors:page=0:size=10:sort=UNSORTED";
        PageResponse<AuthorResponse> cached = PageResponse.from(new PageImpl<>(List.of(authorResponse), pageable, 1));

        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get(key)).thenReturn(cached);

        PageResponse<AuthorResponse> result = authorService.getAllAuthors(pageable);

        assertThat(result).isSameAs(cached);
        verifyNoInteractions(authorRepository);
    }

    @Test
    @DisplayName("search by name")
    void searchByName() {
        List<AuthorResponse> authors = new ArrayList<>();
        authors.add(authorResponse);

        when(authorRepository.searchByName("user1")).thenReturn(Collections.singletonList(author));
        when(authorMapper.toDto(author)).thenReturn(authorResponse);

        List<AuthorResponse> result = authorService.searchByName("user1");

        assertThat(result).isEqualTo(authors);
    }

    @Test
    @DisplayName("search by name not found")
    void searchByNameNotFound() {
        when(authorRepository.searchByName("user1")).thenReturn(List.of());

        assertThrows(AuthorNotFound.class, () -> authorService.searchByName("user1"));

        verify(authorMapper, never()).toDto(any());
    }
}


