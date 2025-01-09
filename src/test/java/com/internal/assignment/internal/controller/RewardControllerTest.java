package com.internal.assignment.internal.controller;


import com.internal.assignment.internal.service.RewardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RewardControllerTest {

    @Mock
    private RewardService rewardService;

    @InjectMocks
    private RewardController rewardController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(rewardController).build();
    }

    @Test
    public void testGetRewards() throws Exception {
        Long customerId = 1L;

        // Prepare the mock return value for the service
        Map<String, Integer> rewards = new HashMap<>();
        rewards.put("points", 150);
        rewards.put("bonus", 50);

        when(rewardService.calculateRewards(customerId)).thenReturn(rewards);

        // Perform the GET request and assert the response
        mockMvc.perform(get("/api/rewards/{customerId}", customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.points").value(150))
                .andExpect(jsonPath("$.bonus").value(50));
        verify(rewardService, times(1)).calculateRewards(customerId);
    }

    @Test
    public void testGetRewardsWhenNoRewards() throws Exception {
        Long customerId = 2L;

        Map<String, Integer> rewards = new HashMap<>();
        when(rewardService.calculateRewards(customerId)).thenReturn(rewards);
        mockMvc.perform(get("/api/rewards/{customerId}", customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
        verify(rewardService, times(1)).calculateRewards(customerId);
    }

    @Test
    public void testGetRewardsWhenServiceThrowsException() throws Exception {
        Long customerId = 3L;
        when(rewardService.calculateRewards(customerId)).thenThrow(new RuntimeException("Service error"));
        mockMvc.perform(get("/api/rewards/{customerId}", customerId))
                .andExpect(status().isInternalServerError())
                .andExpect(result -> result.getResolvedException().getClass().equals(RuntimeException.class))
                .andExpect(result -> result.getResolvedException().getMessage().equals("Service error"));
        verify(rewardService, times(1)).calculateRewards(customerId);
    }
}

