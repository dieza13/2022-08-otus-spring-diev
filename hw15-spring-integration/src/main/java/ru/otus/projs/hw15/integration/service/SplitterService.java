package ru.otus.projs.hw15.integration.service;

import ru.otus.projs.hw15.model.dto.ContractInfo;

import java.util.List;

public interface SplitterService {

    List<?> splitContractInfo(ContractInfo contractInfo);

}
