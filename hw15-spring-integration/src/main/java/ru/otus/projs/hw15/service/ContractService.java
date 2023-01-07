package ru.otus.projs.hw15.service;

import ru.otus.projs.hw15.model.Contract;
import ru.otus.projs.hw15.model.Rule;

public interface ContractService {

    Contract generateContract(Rule rule);

}
