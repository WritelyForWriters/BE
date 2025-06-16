package writeon.domain.common.enums;

import org.springframework.http.HttpStatus;

public interface CodeInfo {

    HttpStatus getStatus();
    String getCode();
    String getMessage();
}
