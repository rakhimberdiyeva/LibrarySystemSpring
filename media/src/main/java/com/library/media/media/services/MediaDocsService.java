package com.library.media.media.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface MediaDocsService {
    public String uploadDocs(long userId, MultipartFile file) throws IOException;
    String downloadDocs(String filename);
}
