package com.library.media.media.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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
