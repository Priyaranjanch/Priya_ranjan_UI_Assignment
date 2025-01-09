package com.internal.assignment.internal.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Validated
@Table(name = "customer_transactions")
public class CustomerTransaction {

    @Id
    @NotNull(message = "Id is Mandatory")
    @Column(name="ID")
    private Long id;

    @NotNull(message = "CustomerID is mandatory")
    @Column(name="CUSTOMER_ID")
    private Long customerId;

    @NotNull(message = "Amount is required")
    @Column(name="AMOUNT")
    private double amount;

    @NotNull(message = "Date is Mandatory")
    @Column(name="DATE")
    private LocalDate date;

}
