package org.example.service;

import org.example.dao.CustomerDAO;
import org.example.dao.CustomerDAOImpl;
import org.example.entity.Customer;
import org.example.entity.Rental;

import java.util.List;
import java.util.Optional;

public class CustomerServiceImpl implements CustomerService {

    CustomerDAO customerDAO = new CustomerDAOImpl();

    @Override
    public void addCustomer(Customer customer) {
        customerDAO.addCustomer(customer);

    }

    @Override
    public void removeCustomer(long id) {
        customerDAO.removeCustomer(id);

    }

    @Override
    public Customer findById(long id) {
        Optional<Customer> optionalCustomer = customerDAO.findById(id);
        return optionalCustomer.orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
    }

    @Override
    public List<Customer> findAll() {
        return customerDAO.findAll();
    }

    @Override
    public void addRental(Customer customer, Rental rental) {
        customerDAO.addRental(customer, rental);
    }

    @Override
    public void removeRental(long id) {
        customerDAO.removeRental(id);
    }

    @Override
    public Rental findRental(long id) {
        Optional<Rental> optionalRental = customerDAO.findRental(id);
        return optionalRental.orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
    }
}
