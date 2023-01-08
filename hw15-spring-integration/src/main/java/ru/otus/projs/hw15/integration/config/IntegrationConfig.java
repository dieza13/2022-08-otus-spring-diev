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
import org.springframework.integration.http.inbound.HttpRequestHandlingMessagingGateway;
import org.springframework.integration.http.inbound.RequestMapping;
import org.springframework.integration.http.outbound.HttpRequestExecutingMessageHandler;
import org.springframework.messaging.Message;
import ru.otus.projs.hw15.integration.service.SplitterService;
import ru.otus.projs.hw15.model.Contract;
import ru.otus.projs.hw15.model.Customer;
import ru.otus.projs.hw15.model.Document;
import ru.otus.projs.hw15.model.Rule;
import ru.otus.projs.hw15.model.dto.ContractInfo;
import ru.otus.projs.hw15.model.dto.CustomerInfo;
import ru.otus.projs.hw15.model.dto.DocumentInfo;
import ru.otus.projs.hw15.model.dto.RuleInfo;

import java.util.Collection;

@Configuration
@EnableIntegration
@RequiredArgsConstructor
public class IntegrationConfig {

    private final SplitterService splitterService;
    private final ServicesConfig servicesConfig;
    private static final String CONTRACT_SPLITTER_INPUT_CHANNEL = "contractSplitterInputChannel";
    private static final String MSG_PARTS_ROUTER_INPUT_CHANNEL = "messagePartsRouterInputChannel";
    private static final String ROUTER_RESULT_CHANNEL = "routerResultChannel";

    @Bean
    public IntegrationFlow inbound() {
        return IntegrationFlows.from(inGateway())
                .channel(CONTRACT_SPLITTER_INPUT_CHANNEL)
                .split(ContractInfo.class, this::splitItem)
                .gateway(routeFlow())
                .aggregate(a -> a.outputProcessor(g -> aggregateContract(g.getMessages())))
                .get();
    }


    @Bean
    public HttpRequestHandlingMessagingGateway inGateway() {
        HttpRequestHandlingMessagingGateway gateway =
                new HttpRequestHandlingMessagingGateway(true);
        gateway.setRequestMapping(inRequestMapping());
        gateway.setRequestPayloadTypeClass(ContractInfo.class);
        return gateway;
    }
    @Bean
    public RequestMapping inRequestMapping() {
        RequestMapping requestMapping = new RequestMapping();
        requestMapping.setPathPatterns("/contract/draft");
        requestMapping.setMethods(HttpMethod.POST);
        return requestMapping;
    }



    @Splitter(inputChannel = CONTRACT_SPLITTER_INPUT_CHANNEL, outputChannel = MSG_PARTS_ROUTER_INPUT_CHANNEL)
    public Collection<?> splitItem(ContractInfo contractInfo) {
        return splitterService.splitContractInfo(contractInfo);
    }

    @Bean
    public HttpRequestExecutingMessageHandler methodGetCustomerOutbound() {
        return getHttpMessageHandler(getServiceAddress("customerService","customer"), Customer.class);
    }

    @Bean
    public HttpRequestExecutingMessageHandler methodGenerateHeadContractOutbound() {
        return getHttpMessageHandler(getServiceAddress("contractService","contract/head"), Contract.class);
    }

    @Bean
    public HttpRequestExecutingMessageHandler methodGetDocumentOutbound() {
        return getHttpMessageHandler(getServiceAddress("documentService","document"), Document.class);
    }

    @Bean
    public HttpRequestExecutingMessageHandler methodGetRuleOutbound() {
        return getHttpMessageHandler(getServiceAddress("ruleService","rule"), Rule.class);
    }

    @Aggregator(inputChannel = ROUTER_RESULT_CHANNEL)
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
                .handle(methodGetRuleOutbound())
                .handle(methodGenerateHeadContractOutbound())
                ;
    }

    private IntegrationFlow routeFlow() {
        return IntegrationFlows.from(MSG_PARTS_ROUTER_INPUT_CHANNEL)
                .<Object, Object>route(Object::getClass,
                        m -> m
                                .subFlowMapping(CustomerInfo.class, sf -> sf.handle(methodGetCustomerOutbound()))
                                .subFlowMapping(DocumentInfo.class, sf -> sf.handle(methodGetDocumentOutbound()))
                                .subFlowMapping(RuleInfo.class, ruleFlow())
                )
                .channel(ROUTER_RESULT_CHANNEL)
                .get();
    }

    private String getServiceAddress(String service, String path) {
        return String.format("%s/%s", servicesConfig.getHost(service), path);
    }
}
