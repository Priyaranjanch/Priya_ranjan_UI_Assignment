package com.internal.assignment.internal.repository;

import com.internal.assignment.internal.Entity.RewardPoints;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RewardPointsRepository extends JpaRepository<RewardPoints, Long> {
    List<RewardPoints> findByCustomerId(Long customerId);
}
