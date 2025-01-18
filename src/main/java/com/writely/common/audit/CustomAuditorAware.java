package com.writely.common.audit;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;
import java.util.UUID;

public class CustomAuditorAware implements AuditorAware<UUID> {

  @Override
  public Optional<UUID> getCurrentAuditor() {
    return Optional.of(UUID.randomUUID());
  }
}
