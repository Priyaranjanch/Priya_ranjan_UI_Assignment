package com.internal.assignment.internal.controller;

import com.internal.assignment.internal.Entity.RewardPoints;
import com.internal.assignment.internal.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rewards")
public class RewardController {

    @Autowired
    private RewardService rewardService;

    @GetMapping("/{customerId}")
    public Map<String, Integer> getRewards(@PathVariable Long customerId) {
        return rewardService.calculateRewards(customerId);
    }

    @GetMapping("/all/{customerId}")
    public ResponseEntity<List<RewardPoints>> getAllRewardPoints(@PathVariable Long customerId) {
        List<RewardPoints> rewardPoints = rewardService.getAllRewardPoints(customerId);
        return ResponseEntity.ok(rewardPoints);
    }

    /**
     * Calculate and retrieve the total reward points for a customer.
     *
     * @param customerId The ID of the customer.
     * @return Total reward points for the customer.
     */
    @GetMapping("/total/{customerId}")
    public ResponseEntity<Integer> calculateTotalRewards(@PathVariable Long customerId) {
        int totalRewards = rewardService.calculateTotalRewardPoints(customerId);
        return ResponseEntity.ok(totalRewards);
    }
}