package org.example.service.sequrity.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dao.entity.RefreshToken;
import org.example.dao.repository.RefreshTokenRepository;
import org.example.dao.repository.UserRepository;
import org.example.service.exception.TokenRefreshException;
import org.example.service.sequrity.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${app.security.refreshTokenDurationMs}")
    private int refreshTokenDurationMs;
    private final RefreshTokenRepository refreshTokenRepo;
    private final UserRepository userRepo;

    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(userRepo.findById(userId).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepo.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepo.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }
}

