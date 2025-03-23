package writeon.api.common.audit;

import org.springframework.data.domain.AuditorAware;
import writeon.api.common.util.MemberUtil;

import java.util.Optional;
import java.util.UUID;

public class CustomAuditorAware implements AuditorAware<UUID> {

  @Override
  public Optional<UUID> getCurrentAuditor() {
    UUID memberId = MemberUtil.getMemberId();
    return memberId != null ? Optional.of(memberId) : Optional.empty();
  }
}
