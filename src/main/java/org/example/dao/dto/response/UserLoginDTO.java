package org.example.dao.dto.response;


public record UserLoginDTO(
        Long id,
        String username,
        String email
) {
}

