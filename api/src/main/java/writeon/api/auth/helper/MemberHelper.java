package writeon.api.auth.helper;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import writeon.domain.common.MemberSession;

@UtilityClass
public class MemberHelper {

    public MemberSession getMemberSession() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context.getAuthentication().getPrincipal() instanceof MemberSession memberSession) {
            return memberSession;
        }
        return null;
    }

}
