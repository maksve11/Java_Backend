package com.maksvell.service.interfaces;

import com.maksvell.entity.RefreshToken;
import org.springframework.stereotype.Service;

@Service
public interface IRefreshTokenService {
    RefreshToken findByToken(String token);

    RefreshToken findByUserId(Long userId);

    RefreshToken createRefreshToken(Long userId);

    RefreshToken verifyExpiration(RefreshToken token);

    int deleteByUserId(Long userId);
}
