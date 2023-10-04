package org.example.dao;

import org.example.entity.Customer;
import org.example.entity.Rental;

import java.util.List;
import java.util.Optional;

public interface CustomerDAO {
    void addCustomer(Customer customer);
    void removeCustomer(long id);
    Optional<Customer> findById(long id);
    List<Customer> findAll();
    void addRental(Customer customer, Rental rental);
    void removeRental(long id);
    Optional<Rental> findRental(long id);
}
