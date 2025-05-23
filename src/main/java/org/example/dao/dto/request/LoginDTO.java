package org.example.dao.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "ДТО для логина пользователя.")
public record LoginDTO(
        @NotBlank
        @Schema(description = "Почта для логина", example = "Lipsar")
        String email,
        @NotBlank
        @Schema(description = "Пароль пользователя для логина", example = "admin111")
        String password
) {
}
