package com.maksvell.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RefreshTokenResponseDto {
    @NotNull
    private String accessToken;

    @NotNull
    private String refreshToken;

    private final String tokenType = "Bearer";
}

