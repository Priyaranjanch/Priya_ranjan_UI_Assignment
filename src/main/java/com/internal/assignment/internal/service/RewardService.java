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

    public Map<String, Integer> calculateRewards(Long customerId) {
        Map<String, Integer> monthlyRewards = new HashMap<>();
        int totalRewards = 0;

        for (Month month : Month.values()) {
            LocalDate startDate = LocalDate.of(2023, month, 1);
            LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

            List<CustomerTransaction> transactions = transactionRepository.findByCustomerId(customerId);
            int monthlyPoints = transactions.stream().mapToInt(this::calculatePoints).sum();

            RewardPoints rewardPoints = new RewardPoints();
            rewardPoints.setCustomerId(customerId);
            rewardPoints.setMonth(month.toString());
            rewardPoints.setYear(2023);
            rewardPoints.setPoints(monthlyPoints);
            rewardPointsRepository.save(rewardPoints);

            monthlyRewards.put(month.toString(), monthlyPoints);
            totalRewards += monthlyPoints;
        }

        monthlyRewards.put("TOTAL", totalRewards);
        return monthlyRewards;
    }

    private int calculatePoints(CustomerTransaction transaction) {
        double amount = transaction.getAmount();
        int points = 0;

        if (amount > 100) {
            points += (amount - 100) * 2;
            amount = 100;
        }
        if (amount > 50) {
            points += (amount - 50);
        }
        return points;
    }

}

