package com.writely.auth.domain;

import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
public class BaseToken {
    @Id
    protected String tokenString;

    public BaseToken(String tokenString) {
        this.tokenString = tokenString;
    }
}
