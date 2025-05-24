package org.example.data.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDTO {
    private UserLoginDTO userDTO;
    private String accessToken;
    private String refreshToken;

}

