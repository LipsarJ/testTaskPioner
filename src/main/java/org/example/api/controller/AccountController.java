package org.example.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.example.api.responses.CommonErrorApiResponsesWith404;
import org.example.api.responses.SimpleResponse;
import org.example.dao.dto.request.TransferDTO;
import org.example.service.AccountService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @Operation(summary = "Перевести деньги от одного пользователя, который вошёл в систему другому")
    @ApiResponse(responseCode = "200", description = "Успешное выполнение операции",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = SimpleResponse.class)))
    @CommonErrorApiResponsesWith404
    @PostMapping("/transfer")
    public void transferMoney(@RequestBody TransferDTO transferDTO){
        accountService.transferMoney(transferDTO);
    }
}
