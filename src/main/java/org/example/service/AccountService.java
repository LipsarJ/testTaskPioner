package org.example.service;

import org.example.dao.dto.request.TransferDTO;

public interface AccountService {
    void increaseBalances();

    void transferMoney(TransferDTO transferDTO);
}
