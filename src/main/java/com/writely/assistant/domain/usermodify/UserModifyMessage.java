package com.writely.assistant.domain.usermodify;

import com.writely.assistant.domain.MessageContent;
import com.writely.assistant.domain.enums.MessageSenderRole;
import com.writely.common.domain.BaseAuditTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "user_modify_message")
public class UserModifyMessage extends BaseAuditTimeEntity {

    @Id
    @Column(updatable = false, nullable = false)
    private UUID id = UUID.randomUUID();

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Embedded
    private MessageContent messageContent;

    @Column(name = "prompt", nullable = false)
    private String prompt;

    public UserModifyMessage(UUID productId, MessageSenderRole role, String content, String prompt) {
        this.productId = productId;
        this.messageContent = new MessageContent(role, content);
        this.prompt = prompt;
    }

    public MessageSenderRole getRole() {
        return messageContent.getRole();
    }

    public String getContent() {
        return messageContent.getContent();
    }
}
