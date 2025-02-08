package com.writely.assistant.domain.automodify;

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
@Table(name = "auto_modify_message")
public class AutoModifyMessage extends BaseAuditTimeEntity {

    @Id
    @Column(updatable = false, nullable = false)
    private UUID id = UUID.randomUUID();

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Embedded
    private MessageContent messageContent;

    public AutoModifyMessage(UUID productId, MessageSenderRole role, String content) {
        this.productId = productId;
        this.messageContent = new MessageContent(role, content);
    }

    public MessageSenderRole getRole() {
        return messageContent.getRole();
    }

    public String getContent() {
        return messageContent.getContent();
    }
}
