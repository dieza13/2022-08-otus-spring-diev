package ru.otus.projs.hw15.service;

import org.springframework.stereotype.Service;
import ru.otus.projs.hw15.model.Contract;
import ru.otus.projs.hw15.model.ProductType;
import ru.otus.projs.hw15.model.Rule;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ContractServiceImpl implements ContractService {

    Map<ProductType, ContractHeadParamResolver> availableSeriesNumber = new HashMap<>() {{
        put(ProductType.Credit, new ContractHeadParamResolver("CCC"));
        put(ProductType.Deposit, new ContractHeadParamResolver("DDD"));
        put(ProductType.Insurance, new ContractHeadParamResolver("III"));
    }};

    @Override
    public Contract generateContract(Rule rule) {
        ContractHeadParamResolver paramResolver = availableSeriesNumber.get(rule.getType());
        return Contract.builder()
                .series(paramResolver.getSeries())
                .number(paramResolver.getNumber())
                .rule(rule)
                .build();
    }

    private static class ContractHeadParamResolver {
        public ContractHeadParamResolver(String series) {
            this.series = series;
        }
        private final String series;
        private final AtomicInteger numberSequence = new AtomicInteger(0);

        public String getSeries() {
            return this.series;
        }

        public String getNumber() {
            int num = this.numberSequence.incrementAndGet();
            return String.format("%010d", num);
        }
    }
}
