package com.library.media.media.repostirory;

import com.library.media.media.MediaCover;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaCoverRepository extends JpaRepository<MediaCover, Long> {
}
