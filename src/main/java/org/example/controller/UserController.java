package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.data.dto.CommonErrorApiResponses;
import org.example.data.dto.CommonErrorApiResponsesWith404;
import org.example.data.dto.SimpleResponse;
import org.example.data.dto.request.SignUpDTO;
import org.example.data.dto.request.UpdateUserDTO;
import org.example.data.dto.response.PageDTO;
import org.example.data.dto.response.ResponseUserDTO;
import org.example.data.repository.filter.FilterUserParam;
import org.example.service.UserService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Получить страницу пользователей с фильтрами")
    @ApiResponse(responseCode = "200", description = "Успешное выполнение операции",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = SimpleResponse.class)))
    @CommonErrorApiResponses
    @GetMapping
    public PageDTO<ResponseUserDTO> getUsers(@ParameterObject @Valid @Parameter @Schema(description = "Параметры фильтрации") FilterUserParam filterParam,
                                             @Schema(name = "Параметры пагинации.", implementation = Pageable.class) Pageable pageable) {
        Page<ResponseUserDTO> userInfoPage = userService.getUsersWithFilterAndPagination(filterParam, pageable);
        return new PageDTO<>(
                userInfoPage.getContent(),
                userInfoPage.getTotalElements(),
                pageable.getPageNumber(),
                pageable.getPageSize()
        );
    }

    @Operation(summary = "Создать пользователя")
    @ApiResponse(responseCode = "200", description = "Успешное выполнение операции",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = SimpleResponse.class)))
    @CommonErrorApiResponses
    @PostMapping("/create")
    public ResponseEntity<ResponseUserDTO> createUser(@RequestBody @Valid SignUpDTO signUpDTO) {
        return ResponseEntity.ok().body(userService.createUser(signUpDTO));
    }

    @Operation(summary = "Обновить данные пользователя")
    @ApiResponse(responseCode = "200", description = "Успешное выполнение операции",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = SimpleResponse.class)))
    @CommonErrorApiResponsesWith404
    @PutMapping("/update")
    public ResponseEntity<ResponseUserDTO> updateUser(@RequestBody @Valid UpdateUserDTO updateUserDTO) {
        return ResponseEntity.ok().body(userService.updateUser(updateUserDTO));
    }
}
