package com.maksvell.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RefreshTokenDto {
    @NotNull
    private String accessToken;

    @NotNull
    private String refreshToken;

    private final String tokenType = "Bearer";
}
