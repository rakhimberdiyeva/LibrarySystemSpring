package com.library.media.media.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface MediaCoverService {
    public String uploadCover(long userId, long bookId, MultipartFile file) throws IOException;
    String downloadCover(String filename);
}
