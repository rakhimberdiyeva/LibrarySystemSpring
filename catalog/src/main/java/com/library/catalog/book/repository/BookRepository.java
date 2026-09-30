package com.library.catalog.book.repository;

import com.library.catalog.book.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(b.description) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Book> searchByTitleOrDesc(@Param("search") String search);

    @Modifying
    @Query("UPDATE Book b SET b.copiesAvailable = b.copiesAvailable - 1 WHERE b.id = :bookId")
    void reserveBook(@Param("bookId") long bookId);

    @Modifying
    @Query("UPDATE Book b SET b.copiesAvailable = b.copiesAvailable + 1 WHERE b.id = :bookId")
    void releaseBook(@Param("bookId") long bookId);

    @EntityGraph(attributePaths = "author")
    Page<Book> findAll(Pageable pageable);
}
