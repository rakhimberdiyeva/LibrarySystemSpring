package com.library.media.media.controllers;

import com.library.media.media.services.MediaDocsServiceImpl;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/media")
public class MediaController {
    @Autowired
    private MediaDocsServiceImpl mediaDocsService;

    @PostMapping(value = "/upload/docs", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadDocs(
            @Parameter(hidden = true) @RequestHeader("X-User-Id") Long userId,
            @RequestPart("file") MultipartFile file) throws IOException {
        return mediaDocsService.uploadDocs(userId, file);
    }

    @GetMapping("/download/docs/{filename}")
    public String  downloadDocs(@PathVariable String filename){
        return mediaDocsService.downloadDocs(filename);
    }
}
