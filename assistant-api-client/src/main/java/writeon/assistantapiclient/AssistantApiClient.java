package writeon.assistantapiclient;

import com.jayway.jsonpath.JsonPath;
import org.springframework.http.HttpStatusCode;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import writeon.assistantapiclient.request.AutoModifyRequest;
import writeon.assistantapiclient.request.FeedbackRequest;
import writeon.assistantapiclient.request.ResearchRequest;
import writeon.assistantapiclient.request.UserModifyRequest;

public class AssistantApiClient extends WebApiClient{

    public AssistantApiClient(String baseUrl, int connectionTimeoutMs, int readTimeoutMs) {
        super(baseUrl, connectionTimeoutMs, readTimeoutMs);
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
