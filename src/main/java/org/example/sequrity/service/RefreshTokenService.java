package org.example.sequrity.service;

import org.example.data.entity.RefreshToken;

public interface RefreshTokenService {


    RefreshToken createRefreshToken(Long userId);

    RefreshToken verifyExpiration(RefreshToken token);
}
