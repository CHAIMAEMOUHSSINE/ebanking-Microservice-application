package net.chaimae.ebankservice.controllers;

import net.chaimae.ebankservice.entities.BankAccount;
import net.chaimae.ebankservice.repository.BankAccountRepository;
import net.chaimae.ebankservice.services.EbankService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EbankRestController {
    private EbankService ebankService;


    public EbankRestController(EbankService ebankService) {
        this.ebankService = ebankService;

    }
    @GetMapping("/accounts")
    public List<BankAccount> getAllBankAccounts() {
        return ebankService.getBankAccounts();
    }
    @GetMapping("/accounts/{id}")
    public BankAccount getBankAccount(@PathVariable String id) {
        return ebankService.getBankAccountById(id);
    }
    @PostMapping("/accounts")
    public BankAccount save(@RequestBody BankAccount bankAccount) {
        return ebankService.save(bankAccount);
    }
}
