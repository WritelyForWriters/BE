package writeon.api.common.util;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;
import writeon.domain.common.MemberSession;

import java.util.UUID;

@UtilityClass
public class MemberUtil {

    public MemberSession getMember() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof MemberSession member) {
            return member;
        }
        return null;
    }

    public UUID getMemberId() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof MemberSession member) {
            return member.getMemberId();
        }
        return null;
    }
}
