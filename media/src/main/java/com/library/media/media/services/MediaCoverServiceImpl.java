package com.library.media.media.services;

import com.library.media.media.dto.BookResponse;
import com.library.media.media.models.MediaCover;
import com.library.media.media.repostirory.MediaCoverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.UUID;

@Service
public class MediaCoverServiceImpl implements MediaCoverService{
    private final S3Client client;
    private final S3Presigner presigner;

    @Value("${minio.bucket1}")
    private String bucket1;

    @Autowired
    private CatalogClient catalogClient;

    @Autowired
    private MediaCoverRepository mediaCoverRepository;

    public MediaCoverServiceImpl(S3Client client, S3Presigner presigner) {
        this.client = client;
        this.presigner = presigner;
    }

    public String uploadCover(long userId, long bookId, MultipartFile file) throws IOException {
        BookResponse book = catalogClient.getBook(bookId);
        String filename = UUID.randomUUID() + "-" + file.getOriginalFilename();

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucket1)
                .key(filename)
                .contentType(file.getContentType())
                .build();

        try (InputStream stream = file.getInputStream()) {
            client.putObject(request, RequestBody.fromInputStream(stream, file.getSize()));
        }
        mediaCoverRepository.save(new MediaCover(userId, book.getId(), filename));
        return filename;
    }

    public String downloadCover(String filename) {
        return presigner.presignGetObject(
                GetObjectPresignRequest.builder()
                        .signatureDuration(Duration.ofMinutes(15))
                        .getObjectRequest(b -> b.bucket(bucket1).key(filename))
                        .build()
        ).url().toString();
    }

}
