package org.example.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.api.responses.CommonErrorApiResponses;
import org.example.api.responses.CommonErrorApiResponsesWith404;
import org.example.api.responses.Errors;
import org.example.api.responses.SimpleResponse;
import org.example.dao.dto.request.LoginDTO;
import org.example.dao.dto.response.AuthResponseDTO;
import org.example.service.sequrity.service.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;


    @Operation(summary = "Войти в систему")
    @ApiResponse(responseCode = "200", description = "Успешное выполнение операции",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = SimpleResponse.class)))
    @CommonErrorApiResponses
    @PostMapping("signin")
    public ResponseEntity<?> signInUser(@Valid @RequestBody LoginDTO loginDto) {
        try {
            AuthResponseDTO response = authService.signIn(loginDto);

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, response.getAccessToken())
                    .header(HttpHeaders.SET_COOKIE, response.getRefreshToken())
                    .body(response.getUserDTO());

        } catch (AuthenticationException ex) {
            return ResponseEntity.status(org.springframework.http.HttpStatus.UNAUTHORIZED)
                    .body(new SimpleResponse("Invalid username or password", Errors.BAD_CREDENTIALS));
        }
    }

    @Operation(summary = "Выйти из системы")
    @ApiResponse(responseCode = "200", description = "Успешное выполнение операции",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = SimpleResponse.class)))
    @CommonErrorApiResponses
    @PostMapping("signout")
    public ResponseEntity<?> signOutUser() {

        AuthResponseDTO response = authService.signOut();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, response.getAccessToken())
                .header(HttpHeaders.SET_COOKIE, response.getRefreshToken())
                .body(new SimpleResponse("You've been signed out!", null));
    }

    @Operation(summary = "Обновить токен")
    @ApiResponse(responseCode = "200", description = "Успешное выполнение операции",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = SimpleResponse.class)))
    @CommonErrorApiResponsesWith404
    @PostMapping("refreshtoken")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {

        AuthResponseDTO authResponseDTO = authService.refreshToken(request);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, authResponseDTO.getAccessToken())
                .body(new SimpleResponse("Token is refreshed successfully!", null));
    }
}
