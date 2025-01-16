package com.internal.assignment.internal.service;

import com.internal.assignment.internal.Entity.CustomerTransaction;
import com.internal.assignment.internal.Entity.RewardPoints;
import com.internal.assignment.internal.repository.CustomerTransactionRepository;
import com.internal.assignment.internal.repository.RewardPointsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RewardService {

    @Autowired
    private CustomerTransactionRepository transactionRepository;

    @Autowired
    private RewardPointsRepository rewardPointsRepository;

    /**
     * Calculate monthly and total rewards for a customer.
     *
     * @param customerId The ID of the customer.
     * @return Map containing monthly rewards and total rewards.
     */
    public Map<String, Integer> calculateRewards(Long customerId) {
        Map<String, Integer> monthlyRewards = new HashMap<>();
        int totalRewards = 0;

        List<CustomerTransaction> transactions = transactionRepository.findByCustomerId(customerId);

        for (Month month : Month.values()) {
            int monthlyPoints = calculateMonthlyPoints(transactions, month, 2023);

            if (monthlyPoints > 0) {
                saveRewardPoints(customerId, month, 2023, monthlyPoints);
                monthlyRewards.put(month.toString(), monthlyPoints);
            }

            totalRewards += monthlyPoints;
        }

        monthlyRewards.put("TOTAL", totalRewards);
        return monthlyRewards;
    }

    /**
     * Calculate total reward points for a customer.
     *
     * @param customerId The ID of the customer.
     * @return Total reward points.
     */
    public int calculateTotalRewardPoints(Long customerId) {
        return transactionRepository.findByCustomerId(customerId)
                .stream()
                .mapToInt(transaction -> calculatePoints(transaction.getAmount()))
                .sum();
    }

    /**
     * Retrieve all reward points for a customer.
     *
     * @param customerId The ID of the customer.
     * @return List of RewardPoints.
     */
    public List<RewardPoints> getAllRewardPoints(Long customerId) {
        return rewardPointsRepository.findByCustomerId(customerId);
    }

    /**
     * Calculate reward points for a transaction amount.
     *
     * @param amount The transaction amount.
     * @return Reward points earned.
     */
    int calculatePoints(double amount) {
        int points = 0;

        if (amount > 100) {
            points += (int) (amount - 100) * 2; // 2 points for every dollar above $100
            amount = 100; // Remaining amount to calculate 1 point per dollar.
        }

        if (amount > 50) {
            points += (int) (amount - 50); // 1 point for every dollar between $50 and $100
        }

        return points;
    }

    /**
     * Calculate reward points for a specific month.
     *
     * @param transactions List of transactions for the customer.
     * @param month        The month to calculate points for.
     * @param year         The year to calculate points for.
     * @return Total reward points for the month.
     */
    int calculateMonthlyPoints(List<CustomerTransaction> transactions, Month month, int year) {
        return transactions.stream()
                .filter(transaction -> isTransactionInMonth(transaction, month, year))
                .mapToInt(transaction -> calculatePoints(transaction.getAmount()))
                .sum();
    }

    /**
     * Save reward points for a customer for a specific month and year.
     *
     * @param customerId The ID of the customer.
     * @param month      The month for the rewards.
     * @param year       The year for the rewards.
     * @param points     The reward points to save.
     */
    void saveRewardPoints(Long customerId, Month month, int year, int points) {
        RewardPoints rewardPoints = new RewardPoints();
        rewardPoints.setCustomerId(customerId);
        rewardPoints.setMonth(month.toString());
        rewardPoints.setYear(year);
        rewardPoints.setPoints(points);
        rewardPointsRepository.save(rewardPoints);
    }

    /**
     * Check if a transaction occurred in a specific month and year.
     *
     * @param transaction The transaction to check.
     * @param month       The month to check.
     * @param year        The year to check.
     * @return True if the transaction occurred in the specified month and year, false otherwise.
     */
    boolean isTransactionInMonth(CustomerTransaction transaction, Month month, int year) {
        LocalDate transactionDate = transaction.getDate();
        return transactionDate.getYear() == year && transactionDate.getMonth() == month;
    }
}


