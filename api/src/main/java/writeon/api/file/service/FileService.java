package writeon.api.file.service;

import writeon.api.common.exception.BaseException;
import writeon.api.common.util.FileUtil;
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
import writeon.domain.file.enums.FileException;
import writeon.domain.file.enums.FileUploadType;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {
    private final S3Presigner s3Presigner;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${cloud.aws.s3.host}")
    private String hostName;

    @Value("${cloud.aws.s3.presigned-url.expiration-seconds}")
    private Long expirationSeconds;

    public PresignedUrlIssueResponse issuePresignedUrl(PresignedUrlIssueRequest params) {
        final FileUploadType uploadType = params.getFileUploadType();

        final String fileOriginalName = params.getOriginalFileName();
        final String fileExtension = FileUtil.getFileExtension(fileOriginalName);
        final String fileGetPath = uploadType.getPath() + "/" + UUID.randomUUID() + "." + fileExtension;
        final String fileGetUrl = hostName + "/" + fileGetPath;

        if (!uploadType.getAllowedExtensions().contains(fileExtension)) {
            throw new BaseException(FileException.UNSUPPORTED_EXTENSION);
        }

        Map<String, String> metadata = new HashMap<>();
        metadata.put("fileOriginalName", fileOriginalName);
        metadata.put("fileExtension", fileExtension);

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileGetPath)
                .metadata(metadata)
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofSeconds(expirationSeconds))
                .putObjectRequest(objectRequest)
                .build();

        PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(presignRequest);

        return new PresignedUrlIssueResponse(presignedRequest.url().toExternalForm(), fileGetUrl);
    }

}
