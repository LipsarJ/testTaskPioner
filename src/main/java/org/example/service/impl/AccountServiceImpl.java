package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dao.dto.request.TransferDTO;
import org.example.dao.entity.Account;
import org.example.dao.repository.AccountRepository;
import org.example.service.AccountService;
import org.example.service.exception.extend.user.UserNotFoundException;
import org.example.service.sequrity.service.impl.UserContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepo;
    private final UserContext userContext;

    @Scheduled(fixedRate = 30000) // каждые 30 секунд
    @Transactional
    public void increaseBalances() {
        List<Account> accounts = accountRepo.findAllForUpdate(); // with PESSIMISTIC_WRITE lock

        for (Account account : accounts) {
            BigDecimal maxBalance = account.getInitialBalance().multiply(BigDecimal.valueOf(2.07));
            BigDecimal increased = account.getBalance().multiply(BigDecimal.valueOf(1.1));

            if (increased.compareTo(maxBalance) > 0) {
                increased = maxBalance;
            }

            account.setBalance(increased);
        }

        accountRepo.saveAll(accounts);
    }

    @Transactional
    public void transferMoney(TransferDTO transferDTO) {
        Long senderId = userContext.getUserDTO().getId();

        if (transferDTO.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive.");
        }
        if (senderId.equals(transferDTO.getReceiverId())) {
            throw new IllegalArgumentException("Sender and recipient cannot be the same.");
        }

        Account sender = accountRepo.findByUserId(senderId)
                .orElseThrow(() -> new UserNotFoundException("Sender not found"));

        Account recipient = accountRepo.findByUserId(transferDTO.getReceiverId())
                .orElseThrow(() -> new UserNotFoundException("Recipient not found"));

        if (sender.getBalance().compareTo(transferDTO.getAmount()) < 0) {
            throw new IllegalStateException("Not enough funds");
        }

        sender.setBalance(sender.getBalance().subtract(transferDTO.getAmount()));
        recipient.setBalance(recipient.getBalance().add(transferDTO.getAmount()));

        accountRepo.save(sender);
        accountRepo.save(recipient);
    }
}
