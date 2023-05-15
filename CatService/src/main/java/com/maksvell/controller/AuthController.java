package com.maksvell.controller;

import com.maksvell.dto.requests.AuthDto;
import com.maksvell.dto.requests.RefreshTokenRequestDto;
import com.maksvell.dto.response.AuthenticatedUserResponseDto;
import com.maksvell.dto.response.RefreshTokenResponseDto;
import com.maksvell.entity.RefreshToken;
import com.maksvell.entity.User;
import com.maksvell.entity.enums.Role;
import com.maksvell.httpExceptions.UnauthorizedException;
import com.maksvell.mappers.implementations.UserMapper;
import com.maksvell.security.jwt.JwtUtils;
import com.maksvell.service.implementations.AuthService;
import com.maksvell.service.implementations.CustomUserDetailsService;
import com.maksvell.service.implementations.RefreshTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.maksvell.service.implementations.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@Tag(name = "Authentication")
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final CustomUserDetailsService userDetailsServiceImpl;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthController(AuthService authService,
                          JwtUtils jwtUtils,
                          RefreshTokenService refreshTokenService,
                          UserService userService,
                          CustomUserDetailsService userDetailsServiceImpl) {
        this.authService = authService;
        this.jwtUtils = jwtUtils;
        this.refreshTokenService = refreshTokenService;
        this.userService = userService;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @Operation(summary = "Registration path")
    @PostMapping("/registration")
    public ResponseEntity<AuthenticatedUserResponseDto> registration(@Valid @RequestBody AuthDto registrationDto) {
        User user = this.authService.registration(registrationDto);
        user.setActive(true);
        user.getRoles().add(Role.ROLE_USER);
        this.userDetailsServiceImpl.AuthenticateUserInContext(user.getEmail());
        String jwtAccessToken = jwtUtils.generateJwtAccessToken(user);
        String jwtRefreshToken = refreshTokenService.createRefreshToken(user.getId()).getToken();
        return ResponseEntity.ok(
                new AuthenticatedUserResponseDto(jwtAccessToken, jwtRefreshToken, UserMapper.INSTANCE.toDTO(user))
        );
    }

    @Operation(summary = "Log in path")
    @PostMapping("/login")
    public ResponseEntity<AuthenticatedUserResponseDto> login(@Valid @RequestBody AuthDto loginDto) {
        User user = this.authService.login(loginDto);
        this.userDetailsServiceImpl.AuthenticateUserInContext(user.getEmail());
        String jwtAccessToken = jwtUtils.generateJwtAccessToken(user);
        RefreshToken refreshTokenEntity = refreshTokenService.findByUserId(user.getId());
        String jwtRefreshToken;
        if (refreshTokenEntity != null) {
            refreshTokenService.deleteByUserId(user.getId());
        }
        jwtRefreshToken = refreshTokenService.createRefreshToken(user.getId()).getToken();
        return ResponseEntity.ok(
                new AuthenticatedUserResponseDto(jwtAccessToken, jwtRefreshToken, UserMapper.INSTANCE.toDTO(user))
        );
    }

    @Operation(summary = "Refresh authentication")
    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponseDto> refreshAuth(@Valid @RequestBody RefreshTokenRequestDto request) {
        String requestRefreshToken = request.getRefreshToken();
        RefreshToken refreshTokenEntity = refreshTokenService.findByToken(requestRefreshToken);
        if (refreshTokenEntity != null) {
            refreshTokenService.verifyExpiration(refreshTokenEntity);
            User user = userService.findById(refreshTokenEntity.getUserId());
            if (user != null) {
                this.userDetailsServiceImpl.AuthenticateUserInContext(user.getEmail());
                String accessToken = jwtUtils.generateJwtAccessToken(user);
                return ResponseEntity.ok(new RefreshTokenResponseDto(accessToken, requestRefreshToken));
            }
        }
        throw new BadCredentialsException("Invalid refresh token");
    }

    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "Log out path")
    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        try {
            User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Long userId = userDetails.getId();
            refreshTokenService.deleteByUserId(userId);
            return ResponseEntity.ok("Log out successful!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new UnauthorizedException();
        }
    }
}
