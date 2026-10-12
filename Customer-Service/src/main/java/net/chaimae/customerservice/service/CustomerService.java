package net.chaimae.customerservice.service;

import net.chaimae.customerservice.entities.Customer;
import net.chaimae.customerservice.repository.CustomerRepository;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @McpTool(description = "Get all customers from the database")
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @McpTool(description= "Find a customer by ID")
    public Customer findCustomerById(@McpToolParam(description = "The cutomer id") Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    @McpTool(description= "Get all customers")
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @McpTool(description= "Save a new  customer ")
    //ajouter un customer
    public Customer saveCustomer(@McpToolParam(description = "The customer to save") Customer customer){
        return customerRepository.save(customer);
    }

}
