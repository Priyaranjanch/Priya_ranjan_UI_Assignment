package com.internal.assignment.internal.controller;


import com.internal.assignment.internal.Entity.CustomerTransaction;
import com.internal.assignment.internal.service.CustomerTransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerTransactionControllerTest {

    @Mock
    private CustomerTransactionService transactionService;

    @InjectMocks
    private CustomerTransactionController transactionController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }

    @Test
    public void testGetTransactions() throws Exception {
        Long customerId = 1L;
        CustomerTransaction transaction1 = new CustomerTransaction();
        transaction1.setCustomerId(1L);
        transaction1.setAmount(100.0);

        CustomerTransaction transaction2 = new CustomerTransaction();
        transaction2.setCustomerId(2L);
        transaction2.setAmount(200.0);

        List<CustomerTransaction> transactions = Arrays.asList(transaction1, transaction2);

        when(transactionService.getTransactions(customerId)).thenReturn(transactions);

        mockMvc.perform(get("/api/transactions/{customerId}", customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].transactionId").value(1L))
                .andExpect(jsonPath("$[0].amount").value(100.0))
                .andExpect(jsonPath("$[1].transactionId").value(2L))
                .andExpect(jsonPath("$[1].amount").value(200.0));

        verify(transactionService, times(1)).getTransactions(customerId);
    }

    @Test
    public void testAddTransaction() throws Exception {
        CustomerTransaction transaction = new CustomerTransaction();
        transaction.setCustomerId(1L);
        transaction.setAmount(100.0);

        when(transactionService.addTransaction(any(CustomerTransaction.class))).thenReturn(transaction);

        mockMvc.perform(post("/api/transactions/addTransaction")
                        .contentType("application/json")
                        .content("{\"amount\":100.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").value(1L))
                .andExpect(jsonPath("$.amount").value(100.0));

        verify(transactionService, times(1)).addTransaction(any(CustomerTransaction.class));
    }

    @Test
    public void testUpdateTransaction() throws Exception {
        Long customerId = 1L;
        CustomerTransaction updatedTransaction = new CustomerTransaction();
        updatedTransaction.setCustomerId(customerId);
        updatedTransaction.setAmount(200.0);

        when(transactionService.updateTransaction(eq(customerId), any(CustomerTransaction.class))).thenReturn(updatedTransaction);

        mockMvc.perform(put("/api/transactions/{transactionId}", customerId)
                        .contentType("application/json")
                        .content("{\"amount\":200.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").value(customerId))
                .andExpect(jsonPath("$.amount").value(200.0));

        verify(transactionService, times(1)).updateTransaction(eq(customerId), any(CustomerTransaction.class));
    }

    @Test
    public void testDeleteTransaction() throws Exception {
        Long transactionId = 1L;

        doNothing().when(transactionService).deleteTransaction(transactionId);

        mockMvc.perform(delete("/api/transactions/{transactionId}", transactionId))
                .andExpect(status().isOk());

        verify(transactionService, times(1)).deleteTransaction(transactionId);
    }
}

