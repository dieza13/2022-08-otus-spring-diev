package ru.otus.projs.hw15.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.projs.hw15.model.Rule;
import ru.otus.projs.hw15.model.dto.RuleInfo;
import ru.otus.projs.hw15.service.RuleService;

@RestController
@RequiredArgsConstructor
public class RuleController {

    private final RuleService ruleService;

    @PostMapping(path = "/rule")
    public Rule getRule(@RequestBody RuleInfo rule) {
        return ruleService.getRuleByName(rule.getName());
    }

}
