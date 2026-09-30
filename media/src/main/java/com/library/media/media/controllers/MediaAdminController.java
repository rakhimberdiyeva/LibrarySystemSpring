package com.library.media.media.controllers;

import com.library.media.media.dto.ApiResponse;
import com.library.media.media.services.MediaCoverServiceImpl;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/admin/media")
public class MediaAdminController {
    @Autowired
    private MediaCoverServiceImpl mediaCoverService;

    @PostMapping(value = "/upload/{bookId}/cover", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<String> uploadCover(
            @PathVariable Long bookId,
            @AuthenticationPrincipal Jwt jwt,
            @RequestPart("file") MultipartFile file) throws IOException {
        Long userId = Long.valueOf(jwt.getClaimAsString("userId"));
        String filename = mediaCoverService.uploadCover(userId, bookId, file);
        return new ApiResponse<>(200, "success", filename);
    }

    @GetMapping("/download/cover/{filename}")
    public ApiResponse<String>  downloadCover(@PathVariable String filename){
        String url = mediaCoverService.downloadCover(filename);
        return new ApiResponse<>(200, "success", url);
    }
}
