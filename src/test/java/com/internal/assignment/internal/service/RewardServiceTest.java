package com.internal.assignment.internal.service;

import com.internal.assignment.internal.Entity.CustomerTransaction;
import com.internal.assignment.internal.Entity.RewardPoints;
import com.internal.assignment.internal.repository.CustomerTransactionRepository;
import com.internal.assignment.internal.repository.RewardPointsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RewardServiceTest {

    @Mock
    private CustomerTransactionRepository transactionRepository;

    @Mock
    private RewardPointsRepository rewardPointsRepository;

    @InjectMocks
    private RewardService rewardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCalculateRewards_Success() {
        Long customerId = 1L;
        List<CustomerTransaction> transactions = Arrays.asList(
                new CustomerTransaction(1L, customerId, 120.0, LocalDate.of(2023, Month.JANUARY, 15)),
                new CustomerTransaction(2L, customerId, 60.0, LocalDate.of(2023, Month.JANUARY, 20)),
                new CustomerTransaction(3L, customerId, 200.0, LocalDate.of(2023, Month.FEBRUARY, 10))
        );

        when(transactionRepository.findByCustomerId(customerId)).thenReturn(transactions);
        Map<String, Integer> rewards = rewardService.calculateRewards(customerId);
        assertEquals(3, rewards.size());
        assertEquals(100, rewards.get("JANUARY"));
        assertEquals(250, rewards.get("FEBRUARY"));
        assertEquals(350, rewards.get("TOTAL"));

        verify(rewardPointsRepository, times(2)).save(any(RewardPoints.class));
    }

    @Test
    void testCalculateTotalRewardPoints_Success() {
        Long customerId = 1L;
        List<CustomerTransaction> transactions = Arrays.asList(
                new CustomerTransaction(1L, customerId, 120.0, LocalDate.of(2023, Month.JANUARY, 15)),
                new CustomerTransaction(2L, customerId, 60.0, LocalDate.of(2023, Month.JANUARY, 20))
        );

        when(transactionRepository.findByCustomerId(customerId)).thenReturn(transactions);
        int totalPoints = rewardService.calculateTotalRewardPoints(customerId);
        assertEquals(100, totalPoints);
    }

    @Test
    void testGetAllRewardPoints_Success() {
        Long customerId = 1L;
        List<RewardPoints> rewardPoints = Arrays.asList(
                new RewardPoints(1L, customerId, "JANUARY", 2023, 150),
                new RewardPoints(2L, customerId, "FEBRUARY", 2023, 250)
        );

        when(rewardPointsRepository.findByCustomerId(customerId)).thenReturn(rewardPoints);
        List<RewardPoints> result = rewardService.getAllRewardPoints(customerId);
        assertEquals(2, result.size());
        assertEquals("JANUARY", result.get(0).getMonth());
        assertEquals(150, result.get(0).getPoints());
    }

    @Test
    void testCalculatePoints_LessThan50() {
        double amount = 40.0;
        int points = rewardService.calculateTotalRewardPoints((long) amount);
        assertEquals(0, points);
    }

    @Test
    void testCalculatePoints_Between50And100() {

        double amount = 75.0;
        int points = rewardService.calculatePoints(amount);
        assertEquals(25, points); // 1 point for every dollar between $50 and $100
    }

    @Test
    void testCalculatePoints_Above100() {

        double amount = 150.0;
        int points = rewardService.calculatePoints(amount);
        assertEquals(150, points); // (50 * 1) + (50 * 2) = 150
    }

    @Test
    void testCalculateMonthlyPoints_NoTransactionsInMonth() {

        List<CustomerTransaction> transactions = Arrays.asList(
                new CustomerTransaction(1L, 1L, 120.0, LocalDate.of(2023, Month.JANUARY, 15)),
                new CustomerTransaction(2L, 1L, 60.0, LocalDate.of(2023, Month.JANUARY, 20))
        );
        int points = rewardService.calculateMonthlyPoints(transactions, Month.FEBRUARY, 2023);
        assertEquals(0, points);
    }

    @Test
    void testIsTransactionInMonth() {

        CustomerTransaction transaction = new CustomerTransaction(1L, 1L, 120.0, LocalDate.of(2023, Month.JANUARY, 15));
        boolean isInMonth = rewardService.isTransactionInMonth(transaction, Month.JANUARY, 2023);
        assertEquals(true, isInMonth);
    }

    @Test
    void testSaveRewardPoints() {

        Long customerId = 1L;
        Month month = Month.JANUARY;
        int year = 2023;
        int points = 150;
        rewardService.saveRewardPoints(customerId, month, year, points);
        verify(rewardPointsRepository, times(1)).save(any(RewardPoints.class));
    }
}