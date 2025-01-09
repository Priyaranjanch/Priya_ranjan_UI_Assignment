package com.internal.assignment.internal.Entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Validated
@Table(name = "Reward_points")
public class RewardPoints {

    @Id
    @NotNull(message = "Id is Mandatory")
    @Column(name="ID")
    private Long id;

    @NotNull(message = "Customer Id is Mandatory")
    @Column(name="CUSTOMER_ID")
    private Long customerId;

    @NotBlank(message = "Month cannot be blank")
    @Column(name="MONTH")
    private String month;

    @NotNull(message = "Year is Mandatory")
    @Column(name="YEAR")
    private int year;

    @NotNull(message = "Points is Mandatory")
    @Column(name="POINTS")
    private int points;
}
