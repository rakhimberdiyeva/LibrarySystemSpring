package com.library.media.media.controllers;

import com.library.media.media.services.MediaCoverServiceImpl;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/admin/media")
public class MediaAdminController {
    @Autowired
    private MediaCoverServiceImpl mediaCoverService;

    @PostMapping(value = "/upload/{bookId}/cover", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadCover(
            @PathVariable Long bookId,
            @Parameter(hidden = true) @RequestHeader("X-User-Id") Long userId,
            @RequestPart("file") MultipartFile file) throws IOException {
        return mediaCoverService.uploadCover(userId, bookId, file);
    }

    @GetMapping("/download/cover/{filename}")
    public String  downloadCover(@PathVariable String filename){
        return mediaCoverService.downloadCover(filename);
    }
}
