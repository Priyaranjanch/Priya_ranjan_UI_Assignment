package com.internal.assignment.internal.service;

import com.internal.assignment.internal.Entity.Customer;
import com.internal.assignment.internal.exceptions.CustomerNotFoundException;
import com.internal.assignment.internal.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

   // @Transactional
    public Customer registerCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Transactional
    public Customer getCustomer(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + customerId));
    }

    @Transactional
    public Customer updateCustomer(Long customerId, Customer updatedCustomer) {
        return customerRepository.findById(customerId)
                .map(customer -> {
                    customer.setName(updatedCustomer.getName());
                    customer.setEmail(updatedCustomer.getEmail());
                    return customerRepository.save(customer);
                }).orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + customerId));
    }

    @Transactional
    public void deleteCustomer(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new CustomerNotFoundException("Customer not found with id: " + customerId);
        }
        customerRepository.deleteById(customerId);
    }
}

