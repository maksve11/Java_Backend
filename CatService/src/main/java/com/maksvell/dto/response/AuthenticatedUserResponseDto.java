package com.maksvell.dto.response;

import com.maksvell.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticatedUserResponseDto {
    private String accessToken;
    private String refreshToken;
    private final String type = "Bearer";
    private UserDto user;
}
