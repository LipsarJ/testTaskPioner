package org.example.service.sequrity.service;

import jakarta.servlet.http.HttpServletRequest;
import org.example.dao.dto.request.LoginDTO;
import org.example.dao.dto.response.AuthResponseDTO;

public interface AuthService {

    AuthResponseDTO signIn(LoginDTO loginDTO);

    AuthResponseDTO signOut();

    AuthResponseDTO refreshToken(HttpServletRequest request);
}
