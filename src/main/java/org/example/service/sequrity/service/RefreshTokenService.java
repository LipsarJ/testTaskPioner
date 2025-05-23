package org.example.service.sequrity.service;

import org.example.dao.entity.RefreshToken;

public interface RefreshTokenService {


    RefreshToken createRefreshToken(Long userId);

    RefreshToken verifyExpiration(RefreshToken token);
}
