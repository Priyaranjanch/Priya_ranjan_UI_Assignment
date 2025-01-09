package com.internal.assignment.internal.controller;

import com.internal.assignment.internal.Entity.Customer;
import com.internal.assignment.internal.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void testRegisterCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");

        when(customerService.registerCustomer(any(Customer.class))).thenReturn(customer);

        mockMvc.perform(post("/api/customers/register")
                        .contentType("application/json")
                        .content("{\"name\":\"John Doe\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(customerService, times(1)).registerCustomer(any(Customer.class));
    }

    @Test
    public void testGetCustomer() throws Exception {
        Long customerId = 1L;
        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setName("John Doe");

        when(customerService.getCustomer(customerId)).thenReturn(customer);

        mockMvc.perform(get("/api/customers/{customerId}", customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(customerId))
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(customerService, times(1)).getCustomer(customerId);
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        Long customerId = 1L;
        Customer existingCustomer = new Customer();
        existingCustomer.setId(customerId);
        existingCustomer.setName("John Doe");

        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(customerId);
        updatedCustomer.setName("John Updated");

        when(customerService.updateCustomer(eq(customerId), any(Customer.class))).thenReturn(updatedCustomer);

        mockMvc.perform(put("/api/customers/{customerId}", customerId)
                        .contentType("application/json")
                        .content("{\"name\":\"John Updated\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(customerId))
                .andExpect(jsonPath("$.name").value("John Updated"));

        verify(customerService, times(1)).updateCustomer(eq(customerId), any(Customer.class));
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        Long customerId = 1L;

        doNothing().when(customerService).deleteCustomer(customerId);

        mockMvc.perform(delete("/api/customers/{customerId}", customerId))
                .andExpect(status().isOk())
                .andExpect(content().string("deleted specific ID"));

        verify(customerService, times(1)).deleteCustomer(customerId);
    }
}


