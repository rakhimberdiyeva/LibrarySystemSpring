package com.library.media.media;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
@Table(name = "media_docs")
public class MediaDocs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "filename", nullable = false)
    private String filename;

    public MediaDocs(long userId, String filename) {
        this.userId = userId;
        this.filename = filename;
    }
}
