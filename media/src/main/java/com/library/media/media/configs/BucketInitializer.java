package com.library.media.media.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BucketInitializer implements ApplicationRunner {

    private final S3Client s3Client;

    @Value("${minio.bucket1}")
    private String bucket1;

    @Value("${minio.bucket2}")
    private String bucket2;

    @Override
    public void run(ApplicationArguments args) {
        for (String bucket : List.of(bucket1, bucket2)) {
            try {
                s3Client.headBucket(b -> b.bucket(bucket));
            } catch (NoSuchBucketException e) {
                s3Client.createBucket(b -> b.bucket(bucket));
            }
        }
    }
}