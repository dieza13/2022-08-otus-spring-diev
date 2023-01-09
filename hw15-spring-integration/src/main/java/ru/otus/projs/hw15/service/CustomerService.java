package ru.otus.projs.hw15.service;

import ru.otus.projs.hw15.model.Customer;

public interface CustomerService {
    Customer getCustomerByName(String name, String lastName);
}
