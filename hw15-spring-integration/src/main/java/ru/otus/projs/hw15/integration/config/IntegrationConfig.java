package ru.otus.projs.hw15.integration.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.annotation.Aggregator;
import org.springframework.integration.annotation.Splitter;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.http.dsl.Http;
import org.springframework.integration.http.outbound.HttpRequestExecutingMessageHandler;
import org.springframework.messaging.Message;
import ru.otus.projs.hw15.model.Contract;
import ru.otus.projs.hw15.model.Customer;
import ru.otus.projs.hw15.model.Document;
import ru.otus.projs.hw15.model.Rule;
import ru.otus.projs.hw15.model.dto.ContractInfo;
import ru.otus.projs.hw15.model.dto.CustomerInfo;
import ru.otus.projs.hw15.model.dto.DocumentInfo;
import ru.otus.projs.hw15.model.dto.RuleInfo;

import java.util.Arrays;
import java.util.Collection;

@Configuration
@EnableIntegration
@RequiredArgsConstructor
public class IntegrationConfig {
    private final ServicesConfig servicesConfig;

    @Bean
    public IntegrationFlow inbound() {
        return IntegrationFlows.from(Http.inboundGateway("/contract/draft")
                        .requestMapping(m -> m.methods(HttpMethod.POST))
                        .requestPayloadType(ContractInfo.class)
                )
                .channel("contractSplitterInputChannel")
                .split(ContractInfo.class, this::splitItem)
                .gateway(routeFlow())
                .aggregate(a -> a.outputProcessor(g -> aggregateContract(g.getMessages())))
                .get();
    }

    @Splitter(inputChannel = "contractSplitterInputChannel", outputChannel = "messagePartsRouterInputChannel")
    public Collection<?> splitItem(ContractInfo contractInfo) {
        CustomerInfo customer = contractInfo.getCustomer();
        return Arrays.asList(
                new DocumentInfo(customer.getName(),customer.getLastName(),contractInfo.getCustomerDocumentType()),
                customer,
                contractInfo.getRule()
        );
    }

    @Bean
    public HttpRequestExecutingMessageHandler getCustomerOutbound() {
        return getHttpMessageHandler(getServiceAddress("customerService","customer"), Customer.class);
    }

    @Bean
    public HttpRequestExecutingMessageHandler generateHeadContractOutbound() {
        return getHttpMessageHandler(getServiceAddress("contractService","contract/head"), Contract.class);
    }

    @Bean
    public HttpRequestExecutingMessageHandler getDocumentOutbound() {
        return getHttpMessageHandler(getServiceAddress("documentService","document"), Document.class);
    }

    @Bean
    public HttpRequestExecutingMessageHandler getRuleOutbound() {
        return getHttpMessageHandler(getServiceAddress("ruleService","rule"), Rule.class);
    }

    @Aggregator(inputChannel = "routerResultChannel")
    public Contract aggregateContract(Collection<Message<?>> parts) {
        Contract contract = (Contract)parts.stream()
                .filter(msg -> msg.getPayload() instanceof Contract)
                .map(Message::getPayload).findFirst().get();

        for (Message msg : parts) {
            if (msg.getPayload() instanceof Document) {
                contract.setAttachedDocument((Document) msg.getPayload());
            } else if (msg.getPayload() instanceof Customer) {
                contract.setOwner((Customer) msg.getPayload());
            }
        }

        return contract;
    }

    private HttpRequestExecutingMessageHandler getHttpMessageHandler(String url, Class<?> clazz) {
        HttpRequestExecutingMessageHandler handler =
                new HttpRequestExecutingMessageHandler(url);
        handler.setHttpMethod(HttpMethod.POST);
        handler.setExpectedResponseType(clazz);
        return handler;
    }

    private IntegrationFlow ruleFlow() {
        return f -> f
                .handle(getRuleOutbound())
                .handle(generateHeadContractOutbound())
                ;
    }

    private IntegrationFlow routeFlow() {
        return IntegrationFlows.from("messagePartsRouterInputChannel")
                .<Object, Object>route(Object::getClass,
                        m -> m
                                .subFlowMapping(CustomerInfo.class, sf -> sf.handle(getCustomerOutbound()))
                                .subFlowMapping(DocumentInfo.class, sf -> sf.handle(getDocumentOutbound()))
                                .subFlowMapping(RuleInfo.class, ruleFlow())
                )
                .channel("routerResultChannel")
                .get();
    }

    private String getServiceAddress(String service, String path) {
        return String.format("%s/%s", servicesConfig.getHost(service), path);
    }
}
