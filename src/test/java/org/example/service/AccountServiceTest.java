package org.example.service;

import org.example.dao.dto.request.TransferDTO;
import org.example.dao.dto.response.ResponseUserDTO;
import org.example.dao.entity.Account;
import org.example.dao.repository.AccountRepository;
import org.example.service.impl.AccountServiceImpl;
import org.example.service.sequrity.service.impl.UserContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepo;
    @Mock
    private UserContext userContext;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    void testSuccessfulTransfer() {
        TransferDTO dto = new TransferDTO(2L, new BigDecimal("50"));
        Account sender = new Account();
        sender.setId(1L);
        sender.setBalance(new BigDecimal("100"));
        Account receiver = new Account();
        receiver.setId(2L);
        receiver.setBalance(new BigDecimal("20"));

        ResponseUserDTO responseUserDTO = new ResponseUserDTO();
        responseUserDTO.setId(1L);


        when(userContext.getUserDTO()).thenReturn(responseUserDTO);
        when(accountRepo.findByUserId(1L)).thenReturn(Optional.of(sender));
        when(accountRepo.findByUserId(2L)).thenReturn(Optional.of(receiver));

        accountService.transferMoney(dto);

        assertEquals(new BigDecimal("50"), sender.getBalance());
        assertEquals(new BigDecimal("70"), receiver.getBalance());

        verify(accountRepo).save(sender);
        verify(accountRepo).save(receiver);
    }

    @Test
    void testTransferFailsIfInsufficientFunds() {
        TransferDTO dto = new TransferDTO(2L, new BigDecimal("100"));
        Account sender = new Account();
        sender.setId(1L);
        sender.setBalance(new BigDecimal("20"));
        Account receiver = new Account();
        receiver.setId(2L);
        receiver.setBalance(new BigDecimal("50"));

        ResponseUserDTO responseUserDTO = new ResponseUserDTO();
        responseUserDTO.setId(1L);

        when(userContext.getUserDTO()).thenReturn(responseUserDTO);
        when(accountRepo.findByUserId(1L)).thenReturn(Optional.of(sender));
        when(accountRepo.findByUserId(2L)).thenReturn(Optional.of(receiver));

        assertThrows(IllegalStateException.class, () -> accountService.transferMoney(dto));
    }
}

