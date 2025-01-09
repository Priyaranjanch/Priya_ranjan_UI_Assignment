package com.internal.assignment.internal.service;

import com.internal.assignment.internal.Entity.CustomerTransaction;
import com.internal.assignment.internal.exceptions.CustomerNotFoundException;
import com.internal.assignment.internal.exceptions.TransactionNotFoundException;
import com.internal.assignment.internal.repository.CustomerRepository;
import com.internal.assignment.internal.repository.CustomerTransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CustomerTransactionService {

    @Autowired
    private CustomerTransactionRepository transactionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public List<CustomerTransaction> getTransactions(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new CustomerNotFoundException("Customer not found with id: " + customerId);
        }
        return transactionRepository.findByCustomerId(customerId);
    }

    @Transactional
    public CustomerTransaction addTransaction(CustomerTransaction transaction) {
        if (!customerRepository.existsById(transaction.getCustomerId())) {
            throw new CustomerNotFoundException("Customer not found with id: " + transaction.getCustomerId());
        }
        return transactionRepository.save(transaction);
    }

    @Transactional
    public CustomerTransaction updateTransaction(Long transactionId, CustomerTransaction updatedTransaction) {
        if (!customerRepository.existsById(updatedTransaction.getCustomerId())) {
            throw new CustomerNotFoundException("Customer not found with id: " + updatedTransaction.getCustomerId());
        }
        return transactionRepository.findById(transactionId)
                .map(transaction -> {
                    transaction.setAmount(updatedTransaction.getAmount());
                    transaction.setDate(updatedTransaction.getDate());
                    return transactionRepository.save(transaction);
                }).orElseThrow(() -> new TransactionNotFoundException("Transaction not found with id: " + transactionId));
    }

    @Transactional
    public void deleteTransaction(Long transactionId) {
        if (!transactionRepository.existsById(transactionId)) {
            throw new TransactionNotFoundException("Transaction not found with id: " + transactionId);
        }
        transactionRepository.deleteById(transactionId);
    }
}

