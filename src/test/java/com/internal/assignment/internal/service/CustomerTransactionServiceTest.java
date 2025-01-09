package com.internal.assignment.internal.service;

import com.internal.assignment.internal.Entity.CustomerTransaction;
import com.internal.assignment.internal.exceptions.CustomerNotFoundException;
import com.internal.assignment.internal.repository.CustomerRepository;
import com.internal.assignment.internal.repository.CustomerTransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerTransactionServiceTest {

    @Mock
    private CustomerTransactionRepository transactionRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerTransactionService transactionService;

    private CustomerTransaction transaction;
    private Long transactionId;
    private Long customerId;

    @BeforeEach
    public void setUp() {

        transactionId = 1L;
        customerId = 1L;

        transaction = new CustomerTransaction();
        transaction.setId(transactionId);
        transaction.setCustomerId(customerId);
        transaction.setAmount(100.0);
        transaction.setDate(LocalDate.parse("2025-01-09"));
    }

    @Test
    public void testGetTransactionsWhenCustomerExists() {

        when(customerRepository.existsById(customerId)).thenReturn(true);
        when(transactionRepository.findByCustomerId(customerId)).thenReturn(List.of(transaction));
        List<CustomerTransaction> transactions = transactionService.getTransactions(customerId);
        assertNotNull(transactions);
        assertEquals(1, transactions.size());
        assertEquals(transactionId, transactions.get(0).getId());
        assertEquals(customerId, transactions.get(0).getCustomerId());

        // Verify that the repository methods were called once
        verify(customerRepository, times(1)).existsById(customerId);
        verify(transactionRepository, times(1)).findByCustomerId(customerId);
    }

    @Test
    public void testGetTransactionsWhenCustomerDoesNotExist() {

        when(customerRepository.existsById(customerId)).thenReturn(false);
        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () -> {
            transactionService.getTransactions(customerId);
        });
        assertEquals("Customer not found with id: " + customerId, exception.getMessage());
        verify(customerRepository, times(1)).existsById(customerId);
    }

    @Test
    public void testAddTransactionWhenCustomerExists() {

        when(customerRepository.existsById(customerId)).thenReturn(true);
        when(transactionRepository.save(transaction)).thenReturn(transaction);


    }
}
