package com.writely.product.response;

import com.writely.product.domain.ProductMemo;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ProductMemoResponse {

    private final UUID id;
    private final String content;
    private final String selectedText;
    private final Integer startIndex;
    private final Integer endIndex;
    private final Boolean isCompleted;

    public ProductMemoResponse(ProductMemo memo) {
        this.id = memo.getId();
        this.content = memo.getContent();
        this.selectedText = memo.getSelectedText();
        this.startIndex = memo.getStartIndex();
        this.endIndex = memo.getEndIndex();
        this.isCompleted = memo.getIsCompleted();
    }
}
