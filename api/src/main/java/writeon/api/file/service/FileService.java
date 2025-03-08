package writeon.api.file.service;

import writeon.api.file.request.PresignedUrlIssueRequest;
import writeon.api.file.response.PresignedUrlIssueResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
    private final int PRESIGNED_URL_EXPIRATION_MINUTES = 10;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public PresignedUrlIssueResponse issuePresignedUrl(PresignedUrlIssueRequest params) {
        final String uploadedFilePath = params.getFileUploadType().getPath() + "/" + UUID.randomUUID();

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(uploadedFilePath)
                .metadata(params.getMetadata())
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(PRESIGNED_URL_EXPIRATION_MINUTES))
                .putObjectRequest(objectRequest)
                .build();

        PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(presignRequest);

        return new PresignedUrlIssueResponse(presignedRequest.url().toExternalForm(), uploadedFilePath);
    }

}
