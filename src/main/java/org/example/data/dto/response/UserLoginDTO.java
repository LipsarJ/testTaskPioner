package org.example.data.dto.response;


public record UserLoginDTO(
        Long id,
        String username,
        String email
) {
}

