package org.example.data.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Schema(description = "ДТО с информацией о пользователе")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseUserDTO {
    @Schema(description = "Идентификатор пользователя", example = "1")
    private Long id;

    @Schema(description = "Имя пользователя", example = "anton")
    private String username;

    @Schema(description = "Дата рождения пользователя", example = "22.08.2002")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private Date birthday;

}



