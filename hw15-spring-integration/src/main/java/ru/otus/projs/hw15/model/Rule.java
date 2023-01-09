package ru.otus.projs.hw15.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Rule {
    private Long id;
    private String ruleName;
    private Double rate;
    private ProductType type;
}
