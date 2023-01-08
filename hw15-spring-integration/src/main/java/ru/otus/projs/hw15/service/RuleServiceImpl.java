package ru.otus.projs.hw15.service;

import org.springframework.stereotype.Service;
import ru.otus.projs.hw15.exception.RuleNotFound;
import ru.otus.projs.hw15.model.ProductType;
import ru.otus.projs.hw15.model.Rule;

import java.util.HashMap;
import java.util.Map;

@Service
public class RuleServiceImpl implements RuleService {

    private final Map<String, Rule> rules = new HashMap<>() {{
        put("СУПЕР_КРЕДИТ", new Rule(1L, "SUPER_CREDIT", 10D, ProductType.Credit));
        put("СУПЕР_ДЕПОЗИТ", new Rule(2L, "SUPER_DEPOSIT", 10D, ProductType.Deposit));
        put("СУПЕР_СТРАХОВКА", new Rule(2L, "SUPER_INSURANCE", 10D, ProductType.Insurance));

    }};

    @Override
    public Rule getRuleByName(String name) {
        String key = name.toUpperCase();
        if (rules.containsKey(key)) {
            return rules.get(key);
        } else {
            throw new RuleNotFound();
        }
    }
}
