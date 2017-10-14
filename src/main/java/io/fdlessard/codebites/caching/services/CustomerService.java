package io.fdlessard.codebites.caching.services;

import io.fdlessard.codebites.caching.domain.Customer;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface CustomerService {

    Customer getCustomerById(long id);

    List<Customer> getAllCustomers();

    List<Customer> getCustomers(List<Long> ids);

    void createCustomer(Customer customer);
}
