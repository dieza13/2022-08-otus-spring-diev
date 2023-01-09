package ru.otus.projs.hw15.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ContractInfo {
    private CustomerInfo customer;
    private String customerDocumentType;
    private RuleInfo rule;
}
