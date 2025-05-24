package org.example.service;

import org.example.data.dto.request.TransferDTO;

public interface AccountService {
    void increaseBalances();

    void transferMoney(TransferDTO transferDTO);
}
