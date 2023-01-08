package ru.otus.projs.hw15.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.projs.hw15.model.Customer;
import ru.otus.projs.hw15.model.dto.CustomerInfo;
import ru.otus.projs.hw15.service.CustomerService;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping(path = "/customer")
    public Customer getCustomer(@RequestBody CustomerInfo customerInfo) {
        return customerService.getCustomerByName(customerInfo.getName(), customerInfo.getLastName());
    }

}
