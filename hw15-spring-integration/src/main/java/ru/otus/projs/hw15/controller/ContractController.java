package ru.otus.projs.hw15.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.projs.hw15.model.Contract;
import ru.otus.projs.hw15.model.Rule;
import ru.otus.projs.hw15.service.ContractService;

@RestController
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    @PostMapping(path = "/contract/head")
    public Contract generateNewContractHead(@RequestBody Rule rule) {
        return contractService.generateContract(rule);
    }

}
