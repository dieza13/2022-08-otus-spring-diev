package ru.otus.projs.hw15.integration.service;

import org.springframework.stereotype.Service;
import ru.otus.projs.hw15.model.dto.ContractInfo;
import ru.otus.projs.hw15.model.dto.CustomerInfo;
import ru.otus.projs.hw15.model.dto.DocumentInfo;

import java.util.Arrays;
import java.util.List;

@Service
public class SplitterServiceImpl implements SplitterService{

    public List<?> splitContractInfo(ContractInfo contractInfo) {
        CustomerInfo customer = contractInfo.getCustomer();
        return Arrays.asList(
                new DocumentInfo(customer.getName(),customer.getLastName(),contractInfo.getCustomerDocumentType()),
                customer,
                contractInfo.getRule()
        );
    }

}
