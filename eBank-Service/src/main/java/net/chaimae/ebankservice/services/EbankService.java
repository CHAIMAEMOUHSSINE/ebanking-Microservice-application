
package net.chaimae.ebankservice.services;

import net.chaimae.ebankservice.entities.BankAccount;
import net.chaimae.ebankservice.feign.CustomerRestClient;
import net.chaimae.ebankservice.model.Customer;
import net.chaimae.ebankservice.repository.BankAccountRepository;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
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

    @McpTool(description = "Get all bank accounts")
    public List<BankAccount> getBankAccounts() {
        return accountRepository.findAll();
    }

    @McpTool(description = "get  bank account by its ID")
    public BankAccount getBankAccountById(@McpToolParam(description = "The bank account is") String id) {
        return accountRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Bank account not found"));
    }

    @McpTool(description = "save  bank account for an existing customer")
    public BankAccount save(@McpToolParam(description = "the bank account to save(balance,type,customerId)") BankAccount bankAccount) {
        Customer customer = customerRestClient
                .getCustomerById(bankAccount.getCustomerId());

        if (customer == null) {
            throw new RuntimeException("Customer not found");
        }

        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setCreatedAt(new Date());

        try {
            return accountRepository.save(bankAccount);
        } catch (Exception e) {
            throw new RuntimeException("Error saving bank account: " + e.getMessage());
        }
    }
}
