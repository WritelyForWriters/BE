package com.writely.auth.domain;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JwtPayload {
    private Long iat;
    private Long exp;
    private UUID memberId;
}
