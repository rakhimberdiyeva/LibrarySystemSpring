package com.library.catalog.authors;

import com.library.catalog.author.Author;
import com.library.catalog.author.AuthorRepository;
import com.library.catalog.book.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class AuthorRepositoryTests {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void clean() {
        authorRepository.deleteAll();
    }

    @Test
    @DisplayName("search author by name")
    void searchByName(){
        List<Book> books = new ArrayList<>();
        Author author1 = new Author("user", LocalDate.of(1999, 2, 3), books);
        entityManager.persist(author1);
        Author author2 = new Author("author", LocalDate.of(1988, 2, 3), books);
        entityManager.persist(author2);
        entityManager.flush();

        List<Author> result = authorRepository.searchByName("au");

        assertNotNull(result);
        assertThat(result.get(0).equals(author1));
    }

    @Test
    @DisplayName("find all one page")
    void findAll(){
        List<Book> books = new ArrayList<>();
        Author author1 = new Author("user", LocalDate.of(1999, 2, 3), books);
        entityManager.persist(author1);
        Author author2 = new Author("author", LocalDate.of(1988, 2, 3), books);
        entityManager.persist(author2);
        entityManager.flush();

        Page<Author> result = authorRepository.findAll(PageRequest.of(0, 2, Sort.by("id")));

        assertThat(result.getContent())
                .extracting(Author::getFullname)
                .containsExactly("user", "author");
        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getTotalPages()).isEqualTo(1);
    }

    @Test
    @DisplayName("find all two pages")
    void findAllTwoPages(){
        List<Book> books = new ArrayList<>();
        Author author1 = new Author("user", LocalDate.of(1999, 2, 3), books);
        entityManager.persist(author1);
        Author author2 = new Author("author", LocalDate.of(1988, 2, 3), books);
        entityManager.persist(author2);
        entityManager.flush();

        Page<Author> result = authorRepository.findAll(PageRequest.of(1, 1, Sort.by("id")));

        assertThat(result.getContent())
                .extracting(Author::getFullname)
                .containsExactly( "author");
        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getTotalPages()).isEqualTo(2);
    }

    @Test
    @DisplayName("find all none")
    void findAllNone(){
        Page<Author> result = authorRepository.findAll(PageRequest.of(1, 10, Sort.by("id")));
        System.out.println(result);
        assertThat(result.getTotalElements()).isEqualTo(0);
        assertThat(result.getTotalPages()).isEqualTo(0);
    }
}
