package com.library.catalog.book;

import com.library.catalog.author.Author;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private String description;
    private int isbn;
    private int year;
    private int copiesTotal;
    private int copiesAvailable;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;
}
