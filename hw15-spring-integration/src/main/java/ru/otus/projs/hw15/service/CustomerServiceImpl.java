package ru.otus.projs.hw15.service;

import org.springframework.stereotype.Service;
import ru.otus.projs.hw15.exception.CustomerNotFound;
import ru.otus.projs.hw15.model.Customer;
import ru.otus.projs.hw15.model.CustomerType;

import java.util.HashMap;
import java.util.Map;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final Map<String, Customer> customers = new HashMap<>() {{
        put("NAME1_LASTNAME1", new Customer(1L, "Name1", "LastName1", CustomerType.usual));
        put("NAME2_LASTNAME2", new Customer(2L, "Name2", "LastName2", CustomerType.usual));
        put("NAME3_LASTNAME3", new Customer(3L, "Name3", "LastName3", CustomerType.usual));
        put("NAME4_LASTNAME4", new Customer(4L, "Name4", "LastName4", CustomerType.vip));
        put("NAME5_LASTNAME5", new Customer(5L, "Name5", "LastName5", CustomerType.usual));

    }};

    public Customer getCustomerByName(String name, String lastName) {
        String key = (name + "_" + lastName).toUpperCase();
        if (customers.containsKey(key)) {
            return customers.get(key);
        } else {
            throw  new CustomerNotFound();
        }
    }

}
