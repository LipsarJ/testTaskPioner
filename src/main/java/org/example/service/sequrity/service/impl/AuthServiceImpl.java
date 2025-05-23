package org.example.service.sequrity.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.dao.dto.request.LoginDTO;
import org.example.dao.dto.response.AuthResponseDTO;
import org.example.dao.dto.response.UserLoginDTO;
import org.example.dao.entity.RefreshToken;
import org.example.dao.repository.RefreshTokenRepository;
import org.example.service.exception.TokenRefreshException;
import org.example.service.sequrity.JwtUtils;
import org.example.service.sequrity.UserDetailsImpl;
import org.example.service.sequrity.service.AuthService;
import org.example.service.sequrity.service.RefreshTokenService;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final RefreshTokenService refreshTokenService;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenRepository refreshTokenRepo;

    @Transactional
    public AuthResponseDTO signIn(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        ResponseCookie jwtRefreshCookie = jwtUtils.generateRefreshJwtCookie(refreshToken.getToken());

        UserLoginDTO userLoginDto = new UserLoginDTO(userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail());

        return new AuthResponseDTO(userLoginDto, jwtCookie.toString(), jwtRefreshCookie.toString());
    }

    public AuthResponseDTO signOut() {
        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
        authResponseDTO.setAccessToken(jwtUtils.getCleanJwtCookie().toString());
        authResponseDTO.setRefreshToken(jwtUtils.getCleanJwtRefreshCookie().toString());
        return authResponseDTO;
    }

    public AuthResponseDTO refreshToken(HttpServletRequest request) {
        String refreshToken = jwtUtils.getJwtRefreshFromCookies(request);
        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new TokenRefreshException(refreshToken, "Refresh token is empty!");
        }
        return refreshTokenRepo.findByToken(refreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(user);
                    AuthResponseDTO authResponseDTO = new AuthResponseDTO();
                    authResponseDTO.setAccessToken(jwtCookie.toString());

                    return authResponseDTO;
                })
                .orElseThrow(() -> new TokenRefreshException(refreshToken, "Refresh token is not in the database!"));

    }
}


