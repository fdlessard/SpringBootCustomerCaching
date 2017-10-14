package io.fdlessard.codebites.caching.services;

import io.fdlessard.codebites.caching.domain.Customer;
import io.fdlessard.codebites.caching.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;


    public CustomerServiceImpl( CustomerRepository customerRepository ) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer getCustomerById(long id) {

        LOGGER.info("CustomerServiceImpl.getById({})", id);
        pause();
        return customerRepository.findOne(id);
    }

    @Override
    public List<Customer> getAllCustomers() {

        LOGGER.info("CustomerServiceImpl.getAllCustomers()");
        List<Customer> customers = new ArrayList<>();
        customerRepository.findAll().forEach(customers::add);
        return customers;
    }


    @Override
    public List<Customer> getCustomers(List<Long> ids) {

        LOGGER.info("CustomerServiceImpl.getCustomers({})", ids);

        List<Customer> customers = new ArrayList<>();

        customerRepository.findAll(ids).forEach(customers::add);

        return customers;
    }

    @Override
    public void createCustomer(Customer customer) {

        LOGGER.info("CustomerServiceImpl.createCustomer()");
        customerRepository.save(customer);
    }

    private void pause() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {

        }
    }
}
