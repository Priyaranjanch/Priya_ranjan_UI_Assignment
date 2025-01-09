package com.internal.assignment.internal.controller;

import com.internal.assignment.internal.Entity.CustomerTransaction;
import com.internal.assignment.internal.service.CustomerTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/transactions")
public class CustomerTransactionController {

    @Autowired
    private CustomerTransactionService transactionService;

    @GetMapping("/{customerId}")
    public List<CustomerTransaction> getTransactions(@PathVariable Long customerId) {
        return transactionService.getTransactions(customerId);
    }

    @PostMapping("/addTransaction")
    public CustomerTransaction addTransaction(@Valid @RequestBody CustomerTransaction transaction) {
        return transactionService.addTransaction(transaction);
    }

    @PutMapping("/{transactionId}")
    public CustomerTransaction updateTransaction(@PathVariable Long transactionId, @Valid @RequestBody CustomerTransaction updatedTransaction) {
        return transactionService.updateTransaction(transactionId, updatedTransaction);
    }

    @DeleteMapping("/{transactionId}")
    public void deleteTransaction(@PathVariable Long transactionId) {
        transactionService.deleteTransaction(transactionId);
    }
}
