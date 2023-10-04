package org.example.service;

import org.example.entity.Customer;
import org.example.entity.Rental;


import java.util.List;
import java.util.Optional;

public interface CustomerService {
    void addCustomer(Customer customer);
    void removeCustomer(long id);
    Customer findById(long id);
    List<Customer> findAll();
    void addRental(Customer customer, Rental rental);
    void removeRental(long id);
    Rental findRental(long id);
}
