package com.library.catalog.book;

import com.library.catalog.author.Author;
import com.library.catalog.author.AuthorRepository;
import com.library.catalog.author.dto.AuthorResponse;
import com.library.catalog.author.exceptions.AuthorNotFound;
import com.library.catalog.book.dto.BookCreateRequest;
import com.library.catalog.book.dto.BookResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {
    @Autowired
    protected AuthorRepository authorRepository;

    protected Author mapAuthor(long authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFound("Author not found: " + authorId));
    }

        public Book toEntity(BookCreateRequest dto){
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setDescription(dto.getDescription());
        book.setIsbn(dto.getIsbn());
        book.setYear(dto.getYear());
        book.setCopiesTotal(dto.getCopiesTotal());
        book.setCopiesAvailable(dto.getCopiesAvailable());
        book.setAuthor(mapAuthor(dto.getAuthorId()));
        return book;
    }

    public BookResponse toDto(Book book){
        BookResponse dto = new BookResponse();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setDescription(book.getDescription());
        dto.setIsbn(book.getIsbn());
        dto.setYear(book.getYear());
        dto.setCopiesTotal(book.getCopiesTotal());
        dto.setCopiesAvailable(book.getCopiesAvailable());

        Author author = book.getAuthor();
        if (author != null) {
            AuthorResponse a = new AuthorResponse();
            a.setId(author.getId());
            a.setFullname(author.getFullname());
            dto.setAuthor(a);
        }
        return dto;
    }

}
