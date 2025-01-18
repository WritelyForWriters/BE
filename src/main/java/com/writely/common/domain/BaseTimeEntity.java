package com.writely.common.domain;

import com.writely.common.util.DateTimeUtil;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity implements Serializable {

  @Column(name = "created_at", updatable = false, nullable = false)
  protected LocalDateTime createdAt;

  @Column(name = "updated_at", nullable = false)
  protected LocalDateTime updatedAt;

  @PrePersist
  protected void onPrePersist() {
    this.createdAt = DateTimeUtil.getDateTime();
    this.updatedAt = this.createdAt;
  }

  @PreUpdate
  protected void onPreUpdate() {
    this.updatedAt = DateTimeUtil.getDateTime();
  }
}
