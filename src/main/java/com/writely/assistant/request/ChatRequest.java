package com.writely.assistant.request;

import lombok.Getter;

@Getter
public class ChatRequest {

    private final String query;

    public ChatRequest(String query) {
        this.query = query;
    }
}
