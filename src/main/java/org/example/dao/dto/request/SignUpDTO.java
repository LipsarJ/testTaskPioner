package org.example.dao.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Schema(description = "ДТО регистрации, которое получаем при регистрации пользователя")
public record SignUpDTO(
        @Schema(description = "Имя пользователя для регистрации (от 3 до 20 символов)", example = "Ivan_Iv")
        @NotBlank
        @Size(min = 3, max = 20)
        String username,

        @Schema(description = "Адреса электронной почты пользователя", example = "Ivam.iv@example.com")
        @Size(min = 1, message = "Хотя бы один email обязателен")
        List<@NotBlank @Email @Size(max = 200) String> email,

        @Schema(description = "Телефоны пользователя", example = "79890293123")
        @Size(min = 1, message = "Хотя бы один телефон обязателен")
        List<@NotBlank @Size(min = 13, max = 13) String> phone,

        @Schema(description = "Пароль пользователя (от 5 до 500 символов)", example = "Password123")
        @NotBlank
        @Size(min = 5, max = 500)
        String password,

        @Schema(description = "Дата рождения пользователя", example = "22.08.2002")
        @NotBlank
        @PastOrPresent(message = "Дата рождения не может быть в будущем")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
        LocalDate birthday,

        @Schema(description = "Изначальный баланс пользователя", example = "10.9")
        @NotBlank
        @DecimalMin(value = "0.0", inclusive = true, message = "Баланс не может быть отрицательным")
        BigDecimal balance
) {
}
