
package net.chaimae.ebankservice.services;

import net.chaimae.ebankservice.entities.BankAccount;
import net.chaimae.ebankservice.feign.CustomerRestClient;
import net.chaimae.ebankservice.model.Customer;
import net.chaimae.ebankservice.repository.BankAccountRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class EbankService {

    private final BankAccountRepository accountRepository;
    private final CustomerRestClient customerRestClient;

    public EbankService(
            BankAccountRepository accountRepository,
            CustomerRestClient customerRestClient) {
        this.accountRepository = accountRepository;
        this.customerRestClient = customerRestClient;
    }

    public List<BankAccount> getBankAccounts() {
        return accountRepository.findAll();
    }

    public BankAccount getBankAccountById(String id) {
        return accountRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Bank account not found"));
    }

    public BankAccount save(BankAccount bankAccount) {
        Customer customer = customerRestClient
                .getCustomerById(bankAccount.getCustomerId());

        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setCreatedAt(new Date());

        return accountRepository.save(bankAccount);
    }
}
