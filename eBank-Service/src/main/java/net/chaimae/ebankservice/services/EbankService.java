package net.chaimae.ebankservice.services;

import net.chaimae.ebankservice.entities.BankAccount;
import net.chaimae.ebankservice.repository.BankAccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EbankService {
    private BankAccountRepository accountRepository;

    private EbankService(BankAccountRepository bankAccountRepository) {
        this.accountRepository = bankAccountRepository;

    }

    public List<BankAccount> getBankAccounts() {
        return accountRepository.findAll();
    }

    public BankAccount getBankAccount(String id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bank account not found"));
    }


}
