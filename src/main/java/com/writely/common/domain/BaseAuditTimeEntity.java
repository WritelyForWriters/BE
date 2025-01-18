package com.writely.common.domain;

import com.writely.common.util.DateTimeUtil;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseAuditTimeEntity implements Serializable {

  @CreatedBy
  @Column(name = "created_by", updatable = false, nullable = false)
  protected Long createdBy;

  @LastModifiedBy
  @Column(name = "updated_by", nullable = false)
  protected Long updatedBy;

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
