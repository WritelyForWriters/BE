package writeon.api.assistant.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AssistantPlannerRequest {

    @Schema(title = "작품 ID")
    private UUID productId;
    @Schema(title = "장르")
    private String genre;
    @Schema(title = "로그라인")
    private String logline;
    @Schema(
        title = "생성할 섹션",
        description = """
        생성 가능한 섹션:
        - example: 예시문장
        - geography, history, politics, society, religion: 세계관(지리/역사/정치/사회/종교)
        - economy, technology, lifestyle, language, culture: 세계관(경제/기술/생활/언어/문화)
        - species, occupation, conflict, custom_field: 세계관(종족/직업/갈등/커스텀필드)
        - introduction, development, crisis, conclusion: 줄거리(발단/전개/위기/결말)
        """
    )
    private String section;
    @Schema(title = "프롬프트")
    private String prompt;
}
