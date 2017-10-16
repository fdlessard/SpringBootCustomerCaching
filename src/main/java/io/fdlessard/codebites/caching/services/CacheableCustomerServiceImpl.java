package io.fdlessard.codebites.caching.services;

import io.fdlessard.codebites.caching.CustomerKeyGenerator;
import io.fdlessard.codebites.caching.domain.Customer;
import io.fdlessard.codebites.caching.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service("CacheableCustomerServiceImpl")
public class CacheableCustomerServiceImpl extends CustomerServiceImpl {

    private CacheManager cacheManager;

    private CustomerKeyGenerator customerKeyGenerator;

    public CacheableCustomerServiceImpl(CustomerRepository customerRepository,
                                        CacheManager cacheManager,
                                        CustomerKeyGenerator customerKeyGenerator) {

        super(customerRepository);
        this.cacheManager = cacheManager;
        this.customerKeyGenerator = customerKeyGenerator;
    }

    @Override
    @Cacheable(value = "Customer", keyGenerator = "customerKeyGenerator")
    public Customer getCustomerById(long id) {
        LOGGER.info("CacheableCustomerServiceImpl.getById({})", id);
        return super.getCustomerById(id);
    }

    @Override
    public List<Customer> getCustomers(List<Long> ids) {

        LOGGER.info("CacheableCustomerServiceImpl.getCustomers({})", ids);

        Cache cache = cacheManager.getCache("Customer");

        Map<Long, Customer> customerMap = ids.stream()
                .collect(Collectors.toMap(id -> id, id -> getCachedCustomerById(cache, id)));

        List<Long> uncachedCustomerIds = customerMap.entrySet().stream()
                .filter(e -> e.getValue() == null)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        List<Customer> uncachedCustomers = super.getCustomers(uncachedCustomerIds);

        for (Customer customer : uncachedCustomers) {
            customerMap.put(customer.getId(), customer);
            cache.put(generateKey(customer), customer);
        }

        return ids.stream().map(customerMap::get).collect(Collectors.toList());
    }

    private Customer getCachedCustomerById(Cache cache, long id) {

        Cache.ValueWrapper valueWrapper = cache.get(id);

        if (valueWrapper == null) {
            return null;
        }

        return (Customer) valueWrapper.get();
    }

    private Object generateKey(Customer customer) {
        return customerKeyGenerator.generate(null, null, customer.getId());
    }
}
