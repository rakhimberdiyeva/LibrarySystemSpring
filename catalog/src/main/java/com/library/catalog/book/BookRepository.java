package com.library.catalog.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(b.description) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Book> searchByTitleOrDesc(@Param("search") String search);
}
