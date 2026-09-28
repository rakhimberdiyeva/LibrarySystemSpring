package com.library.media.media;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
@Table(name = "media_covers")
public class MediaCover{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "book_id", nullable = false)
    private long bookId;

    @Column(name = "filename", nullable = false)
    private String filename;

    public MediaCover(long userId, long bookId, String filename) {
        this.userId = userId;
        this.bookId = bookId;
        this.filename = filename;
    }
}

