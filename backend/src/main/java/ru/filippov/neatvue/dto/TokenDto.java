package ru.filippov.neatvue.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenDto {
    private final String accessToken;
    private final String refreshToken;
    private final long accessTokenExpiredIn;
    private final long refreshTokenExpiredIn;
}
