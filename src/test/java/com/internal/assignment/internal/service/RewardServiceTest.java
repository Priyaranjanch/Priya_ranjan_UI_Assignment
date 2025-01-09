package com.internal.assignment.internal.service;

import com.internal.assignment.internal.Entity.CustomerTransaction;
import com.internal.assignment.internal.repository.CustomerTransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class RewardServiceTest {

    @Mock
    private CustomerTransactionRepository transactionRepository;

    @InjectMocks
    private RewardService rewardService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCalculateRewards() {
        Long customerId = 1L;
        LocalDate date = LocalDate.of(2023, 1, 15);
        CustomerTransaction transaction = new CustomerTransaction(1L, customerId, 120, date);
        List<CustomerTransaction> transactions = Arrays.asList(transaction);

        when(transactionRepository.findByCustomerIdAndDateBetween(eq(customerId), any(), any()))
                .thenReturn(transactions);

        Map<String, Integer> rewards = rewardService.calculateRewards(customerId);

        assertEquals(90, rewards.get("JANUARY"));
        assertEquals(90, rewards.get("TOTAL"));
    }
}
