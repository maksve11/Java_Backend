package com.maksvell.service.implementations;

import com.maksvell.entity.RefreshToken;
import com.maksvell.entity.User;
import com.maksvell.httpExceptions.ForbiddenException;
import com.maksvell.httpExceptions.NotFoundException;
import com.maksvell.repository.RefreshTokenRepository;
import com.maksvell.repository.UserRepository;
import com.maksvell.service.interfaces.IRefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService implements IRefreshTokenService {

    private Long refreshTokenDurationMs;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    @Override
    public RefreshToken findByToken(String token) {
        Optional<RefreshToken> refreshTokenEntityOptional = refreshTokenRepository.findByToken(token);
        return refreshTokenEntityOptional.orElse(null);
    }

    @Override
    public RefreshToken findByUserId(Long userId) {
        Optional<RefreshToken> refreshTokenEntityOptional = refreshTokenRepository.findByUserId(userId);
        if (refreshTokenEntityOptional.isPresent()) {
            RefreshToken refreshTokenEntity = refreshTokenEntityOptional.get();
            try{
                return this.verifyExpiration(refreshTokenEntity);
            }catch (ForbiddenException e){
                return null;
            }
        }
        return null;
    }

    @Override
    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();
        Optional<User> userEntityOptional = userRepository.findById(userId);
        if (userEntityOptional.isPresent()) {
            refreshToken.setUserId(userEntityOptional.get().getId());
            refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
            refreshToken.setToken(UUID.randomUUID().toString());
            refreshToken = refreshTokenRepository.save(refreshToken);
            return refreshToken;
        }
        return null;
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new ForbiddenException("Refresh token was expired. Please make a new log in request");
        }
        return token;
    }

    @Override
    public int deleteByUserId(Long userId) {
        Optional<User> userEntityOptional = userRepository.findById(userId);
        if (userEntityOptional.isPresent()) {
            return refreshTokenRepository.deleteByUserId(userEntityOptional.get().getId());
        }
        throw new NotFoundException();
    }
}
