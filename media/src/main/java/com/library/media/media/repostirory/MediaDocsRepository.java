package com.library.media.media.repostirory;

import com.library.media.media.models.MediaDocs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaDocsRepository extends JpaRepository<MediaDocs, Long> {
}
