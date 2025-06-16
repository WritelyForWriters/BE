package writeon.api.common.response;

import java.util.List;

import lombok.Getter;

@Getter
public class CursorResponse<T> {

    private final List<T> contents;
    private final long totalElements;

    private CursorResponse(List<T> contents, long totalElements) {
        this.contents = contents;
        this.totalElements = totalElements;
    }

    public static <T> CursorResponse<T> of(List<T> contents, long totalElements) {
        return new CursorResponse<>(contents, totalElements);
    }
}
