package org.example.sequrity.service;

import jakarta.servlet.http.HttpServletRequest;
import org.example.data.dto.request.LoginDTO;
import org.example.data.dto.response.AuthResponseDTO;

public interface AuthService {

    AuthResponseDTO signIn(LoginDTO loginDTO);

    AuthResponseDTO signOut();

    AuthResponseDTO refreshToken(HttpServletRequest request);
}
