package com.internal.assignment.internal.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
@Table(name = "customer")
public class Customer {

    @Id
    @NotNull(message = "Id is Mandatory")
    @Column(name="ID")
    private Long id;

    @NotBlank(message = "Name is Mandatory")
    @Column(name = "NAME")
    private String name;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    @Column(name = "EMAIL")
    private String email;
}
