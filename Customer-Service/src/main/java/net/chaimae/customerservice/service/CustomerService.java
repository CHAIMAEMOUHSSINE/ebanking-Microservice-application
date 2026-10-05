package net.chaimae.customerservice.service;

import net.chaimae.customerservice.entities.Customer;
import net.chaimae.customerservice.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer findCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }
    //ajouter un customer
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

}
