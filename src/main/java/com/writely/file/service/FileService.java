package com.writely.file.service;

import com.writely.file.request.PresignedUrlIssueRequest;
import com.writely.file.response.PresignedUrlIssueResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {
    private final S3Presigner s3Presigner;
    private final String BUCKET_NAME = "writely-bucket";
    private final int PRESIGNED_URL_EXPIRATION_MINUTES = 10;

    public PresignedUrlIssueResponse issuePresignedUrl(PresignedUrlIssueRequest params) {
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(params.getFileUploadType().getPath() + "/" + UUID.randomUUID())
                .metadata(params.getMetadata())
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(PRESIGNED_URL_EXPIRATION_MINUTES))
                .putObjectRequest(objectRequest)
                .build();

        PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(presignRequest);

        return new PresignedUrlIssueResponse(presignedRequest.url().toExternalForm());
    }

}
