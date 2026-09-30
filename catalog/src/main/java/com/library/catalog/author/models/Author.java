package com.library.catalog.author.models;

import com.library.catalog.book.models.Book;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String fullname;
    private LocalDate birthDate;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Book> books;

    public Author(String fullname, LocalDate birthDate, List<Book> books) {
        this.fullname = fullname;
        this.birthDate = birthDate;
        this.books = books;
    }
}
