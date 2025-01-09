package com.internal.assignment.internal.service;


import com.internal.assignment.internal.Entity.Customer;
import com.internal.assignment.internal.exceptions.CustomerNotFoundException;
import com.internal.assignment.internal.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;
    private Long customerId;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        customerId = 1L;
        customer = new Customer();
        customer.setId(customerId);
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");
    }

    @Test
    public void testRegisterCustomer() {
        when(customerRepository.save(customer)).thenReturn(customer);
        Customer registeredCustomer = customerService.registerCustomer(customer);
        assertNotNull(registeredCustomer);
        assertEquals(customerId, registeredCustomer.getId());
        assertEquals("John Doe", registeredCustomer.getName());
        assertEquals("john.doe@example.com", registeredCustomer.getEmail());
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    public void testGetCustomerWhenExists() {

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        Customer foundCustomer = customerService.getCustomer(customerId);// Assert
        assertNotNull(foundCustomer);
        assertEquals(customerId, foundCustomer.getId());
        assertEquals("John Doe", foundCustomer.getName());
        assertEquals("john.doe@example.com", foundCustomer.getEmail());
        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    public void testGetCustomerWhenNotExists() {
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());
        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () -> {
            customerService.getCustomer(customerId);
        });
        assertEquals("Customer not found with id: " + customerId, exception.getMessage());
        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    public void testUpdateCustomerWhenExists() {
        Customer updatedCustomer = new Customer();
        updatedCustomer.setName("Jane Doe");
        updatedCustomer.setEmail("jane.doe@example.com");

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(customerRepository.save(customer)).thenReturn(customer);
        Customer updatedCustomerResult = customerService.updateCustomer(customerId, updatedCustomer);
        assertNotNull(updatedCustomerResult);
        assertEquals("Jane Doe", updatedCustomerResult.getName());
        assertEquals("jane.doe@example.com", updatedCustomerResult.getEmail());
        verify(customerRepository, times(1)).findById(customerId);
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    public void testUpdateCustomerWhenNotExists() {
        // Arrange
        Customer updatedCustomer = new Customer();
        updatedCustomer.setName("Jane Doe");
        updatedCustomer.setEmail("jane.doe@example.com");

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());
        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () -> {
            customerService.updateCustomer(customerId, updatedCustomer);
        });
        assertEquals("Customer not found with id: " + customerId, exception.getMessage());
        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    public void testDeleteCustomerWhenExists() {
        when(customerRepository.existsById(customerId)).thenReturn(true);
        customerService.deleteCustomer(customerId);
        verify(customerRepository, times(1)).existsById(customerId);
        verify(customerRepository, times(1)).deleteById(customerId);
    }

    @Test
    public void testDeleteCustomerWhenNotExists() {
        when(customerRepository.existsById(customerId)).thenReturn(false);
        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () -> {
            customerService.deleteCustomer(customerId);
        });
        assertEquals("Customer not found with id: " + customerId, exception.getMessage());
        verify(customerRepository, times(1)).existsById(customerId);
    }
}

