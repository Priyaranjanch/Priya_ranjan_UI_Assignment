package com.internal.assignment.internal.controller;

import com.internal.assignment.internal.Entity.RewardPoints;
import com.internal.assignment.internal.service.RewardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RewardControllerTest {

    @Mock
    private RewardService rewardService;

    @InjectMocks
    private RewardController rewardController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetRewards_Success() {
        Long customerId = 1L;
        Map<String, Integer> rewards = Map.of(
                "JANUARY", 150,
                "FEBRUARY", 250,
                "TOTAL", 400
        );
        when(rewardService.calculateRewards(customerId)).thenReturn(rewards);
        Map<String, Integer> response = rewardController.getRewards(customerId);
        assertEquals(3, response.size());
        assertEquals(150, response.get("JANUARY"));
        assertEquals(250, response.get("FEBRUARY"));
        assertEquals(400, response.get("TOTAL"));
        verify(rewardService, times(1)).calculateRewards(customerId);
    }

    @Test
    void testGetAllRewardPoints_Success() {
        // Arrange
        Long customerId = 1L;
        List<RewardPoints> rewardPointsList = Arrays.asList(
                new RewardPoints(1L, customerId, "JANUARY", 2023, 150),
                new RewardPoints(2L, customerId, "FEBRUARY", 2023, 250)
        );
        when(rewardService.getAllRewardPoints(customerId)).thenReturn(rewardPointsList);
        ResponseEntity<List<RewardPoints>> response = rewardController.getAllRewardPoints(customerId);
        assertEquals(2, response.getBody().size());
        assertEquals("JANUARY", response.getBody().get(0).getMonth());
        assertEquals(150, response.getBody().get(0).getPoints());
        verify(rewardService, times(1)).getAllRewardPoints(customerId);
    }

    @Test
    void testCalculateTotalRewards_Success() {// Arrange
        Long customerId = 1L;
        int totalPoints = 400;
        when(rewardService.calculateTotalRewardPoints(customerId)).thenReturn(totalPoints);
        ResponseEntity<Integer> response = rewardController.calculateTotalRewards(customerId);
        assertEquals(400, response.getBody());
        verify(rewardService, times(1)).calculateTotalRewardPoints(customerId);
    }
}

