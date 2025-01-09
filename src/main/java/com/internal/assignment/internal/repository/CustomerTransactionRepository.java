package com.internal.assignment.internal.repository;

import com.internal.assignment.internal.Entity.CustomerTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface CustomerTransactionRepository extends JpaRepository<CustomerTransaction, Long> {
    List<CustomerTransaction> findByCustomerIdAndDateBetween(Long customerId, LocalDate startDate, LocalDate endDate);

    List<CustomerTransaction> findByCustomerId(Long customerId);

   // List<CustomerTransaction> findByCustomerIdAndDateBetween(Long customerId);
}


