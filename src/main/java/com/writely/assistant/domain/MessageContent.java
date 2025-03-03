package com.writely.assistant.domain;

import com.writely.assistant.domain.enums.MessageSenderRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
public class MessageContent {

    @Convert(converter = MessageSenderRole.TypeCodeConverter.class)
    @Column(name = "role", nullable = false)
    private MessageSenderRole role;

    @Column(name = "content", nullable = false)
    private String content;

    public MessageContent(MessageSenderRole role, String content) {
        this.role = role;
        this.content = content;
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
