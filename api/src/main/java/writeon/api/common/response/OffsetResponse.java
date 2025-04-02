package writeon.api.common.response;

import java.util.List;

import lombok.Getter;

@Getter
public class OffsetResponse<T> {

    private final List<T> contents;
    private final int page;
    private final long totalPages;
    private final long totalElements;

    private OffsetResponse(List<T> contents, int page, int size, long totalElements) {
        this.contents = contents;
        this.page = page;
        this.totalPages = (int) Math.ceil((double) totalElements / size);
        this.totalElements = totalElements;
    }

    public static <T> OffsetResponse<T> of(List<T> contents, int page, int size, long totalElements) {
        return new OffsetResponse<>(contents, page, size, totalElements);
    }
}
