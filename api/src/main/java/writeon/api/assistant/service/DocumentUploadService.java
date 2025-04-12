package writeon.api.assistant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import writeon.assistantapiclient.AssistantApiClient;
import writeon.assistantapiclient.request.DocumentUploadRequest;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentUploadService {

    private final AssistantApiClient assistantApiClient;

    public void documentUpload(UUID productId, String content) {
        DocumentUploadRequest request = new DocumentUploadRequest(
            "t" + productId.toString().replaceAll("-", ""),
            content
        );
        assistantApiClient.documentUpload(request)
            .subscribe();
    }
}
