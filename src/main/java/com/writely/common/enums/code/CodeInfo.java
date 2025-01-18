package com.writely.common.enums.code;

import org.springframework.http.HttpStatus;

public interface CodeInfo {

    HttpStatus getStatus();
    String getCode();
    String getMessage();
}
