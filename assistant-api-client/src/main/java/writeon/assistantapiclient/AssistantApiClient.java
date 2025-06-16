package writeon.assistantapiclient;

import com.jayway.jsonpath.JsonPath;
import org.springframework.http.HttpStatusCode;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import writeon.assistantapiclient.request.*;

public class AssistantApiClient extends WebApiClient{

    public AssistantApiClient(String baseUrl, int connectionTimeoutMs, int readTimeoutMs) {
        super(baseUrl, connectionTimeoutMs, readTimeoutMs);
    }

    public Mono<Void> documentUpload(DocumentUploadRequest request) {
        return webClient.post()
            .uri(uriBuilder -> uriBuilder
                .path("/v1/document/upload")
                .build()
            )
            .bodyValue(request)
            .retrieve()
            .onStatus(HttpStatusCode::isError, response ->
                response.bodyToMono(String.class)
                    .doOnNext(errorBody -> System.out.println("Error response: " + errorBody))
                    .flatMap(errorBody -> Mono.error(new RuntimeException(errorBody)))
            )
            .bodyToMono(String.class)
            .then();
    }

    public Mono<String> research(ResearchRequest request) {
        return webClient.post()
            .uri(uriBuilder -> uriBuilder
                .path("/v1/assistant/research")
                .build()
            )
            .bodyValue(request)
            .retrieve()
            .onStatus(HttpStatusCode::isError, response ->
                response.bodyToMono(String.class)
                    .doOnNext(errorBody -> System.out.println("Error response: " + errorBody))
                    .flatMap(errorBody -> Mono.error(new RuntimeException(errorBody)))
            )
            .bodyToMono(String.class)
            .map(responseBody -> JsonPath.read(responseBody, "$.result"));
    }

    public Mono<String> autoModify(AutoModifyRequest request) {
        return webClient.post()
            .uri(uriBuilder -> uriBuilder
                .path("/v1/assistant/auto-modify")
                .build()
            )
            .bodyValue(request)
            .retrieve()
            .onStatus(HttpStatusCode::isError, response ->
                response.bodyToMono(String.class)
                    .doOnNext(errorBody -> System.out.println("Error response: " + errorBody))
                    .flatMap(errorBody -> Mono.error(new RuntimeException(errorBody)))
            )
            .bodyToMono(String.class)
            .map(responseBody -> JsonPath.read(responseBody, "$.result.content"));
    }

    public Flux<String> streamAutoModify(AutoModifyRequest request) {
        return webClient.post()
            .uri(uriBuilder -> uriBuilder
                .path("/v1/assistant/auto-modify/stream")
                .build()
            )
            .bodyValue(request)
            .retrieve()
            .onStatus(HttpStatusCode::isError, response ->
                response.bodyToMono(String.class)
                    .doOnNext(errorBody -> System.out.println("Error response: " + errorBody))
                    .flatMap(errorBody -> Mono.error(new RuntimeException(errorBody)))
            )
            .bodyToFlux(String.class);
    }

    public Mono<String> chat(ChatRequest request) {
        return webClient.post()
            .uri(uriBuilder -> uriBuilder
                .path("/v1/assistant/chat")
                .build()
            )
            .bodyValue(request)
            .retrieve()
            .onStatus(HttpStatusCode::isError, response ->
                response.bodyToMono(String.class)
                    .doOnNext(errorBody -> System.out.println("Error response: " + errorBody))
                    .flatMap(errorBody -> Mono.error(new RuntimeException(errorBody)))
            )
            .bodyToMono(String.class)
            .map(responseBody -> JsonPath.read(responseBody, "$.result.content"));
    }

    public Flux<String> streamChat(ChatRequest request) {
        return webClient.post()
            .uri(uriBuilder -> uriBuilder
                .path("/v1/assistant/chat/stream")
                .build()
            )
            .bodyValue(request)
            .retrieve()
            .onStatus(HttpStatusCode::isError, response ->
                response.bodyToMono(String.class)
                    .doOnNext(errorBody -> System.out.println("Error response: " + errorBody))
                    .flatMap(errorBody -> Mono.error(new RuntimeException(errorBody)))
            )
            .bodyToFlux(String.class);
    }

    public Mono<String> feedback(FeedbackRequest request) {
        return webClient.post()
            .uri(uriBuilder -> uriBuilder
                .path("/v1/assistant/feedback")
                .build()
            )
            .bodyValue(request)
            .retrieve()
            .onStatus(HttpStatusCode::isError, response ->
                response.bodyToMono(String.class)
                    .doOnNext(errorBody -> System.out.println("Error response: " + errorBody))
                    .flatMap(errorBody -> Mono.error(new RuntimeException(errorBody)))
            )
            .bodyToMono(String.class)
            .map(responseBody -> JsonPath.read(responseBody, "$.result.content"));
    }

    public Flux<String> streamFeedback(FeedbackRequest request) {
        return webClient.post()
            .uri(uriBuilder -> uriBuilder
                .path("/v1/assistant/feedback/stream")
                .build()
            )
            .bodyValue(request)
            .retrieve()
            .onStatus(HttpStatusCode::isError, response ->
                response.bodyToMono(String.class)
                    .doOnNext(errorBody -> System.out.println("Error response: " + errorBody))
                    .flatMap(errorBody -> Mono.error(new RuntimeException(errorBody)))
            )
            .bodyToFlux(String.class);
    }

    public Mono<String> plannerGenerate(PlannerGenerateRequest request) {
        return webClient.post()
            .uri(uriBuilder -> uriBuilder
                .path("/v1/planner/generate")
                .build()
            )
            .bodyValue(request)
            .retrieve()
            .onStatus(HttpStatusCode::isError, response ->
                response.bodyToMono(String.class)
                    .doOnNext(errorBody -> System.out.println("Error response: " + errorBody))
                    .flatMap(errorBody -> Mono.error(new RuntimeException(errorBody)))
            )
            .bodyToMono(String.class)
            .map(responseBody -> JsonPath.read(responseBody, "$.result.content"));
    }

    public Flux<String> streamPlannerGenerate(PlannerGenerateRequest request) {
        return webClient.post()
            .uri(uriBuilder -> uriBuilder
                .path("/v1/planner/generate/stream")
                .build()
            )
            .bodyValue(request)
            .retrieve()
            .onStatus(HttpStatusCode::isError, response ->
                response.bodyToMono(String.class)
                    .doOnNext(errorBody -> System.out.println("Error response: " + errorBody))
                    .flatMap(errorBody -> Mono.error(new RuntimeException(errorBody)))
            )
            .bodyToFlux(String.class);
    }

    public Mono<String> userModify(UserModifyRequest request) {
        return webClient.post()
            .uri(uriBuilder -> uriBuilder
                .path("/v1/assistant/user-modify")
                .build()
            )
            .bodyValue(request)
            .retrieve()
            .onStatus(HttpStatusCode::isError, response ->
                response.bodyToMono(String.class)
                    .doOnNext(errorBody -> System.out.println("Error response: " + errorBody))
                    .flatMap(errorBody -> Mono.error(new RuntimeException(errorBody)))
            )
            .bodyToMono(String.class)
            .map(responseBody -> JsonPath.read(responseBody, "$.result.content"));
    }

    public Flux<String> streamUserModify(UserModifyRequest request) {
        return webClient.post()
            .uri(uriBuilder -> uriBuilder
                .path("/v1/assistant/user-modify/stream")
                .build()
            )
            .bodyValue(request)
            .retrieve()
            .onStatus(HttpStatusCode::isError, response ->
                response.bodyToMono(String.class)
                    .doOnNext(errorBody -> System.out.println("Error response: " + errorBody))
                    .flatMap(errorBody -> Mono.error(new RuntimeException(errorBody)))
            )
            .bodyToFlux(String.class);
    }
}
