package io.fdlessard.codebites.caching.repositories;

import io.fdlessard.codebites.caching.domain.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

}
