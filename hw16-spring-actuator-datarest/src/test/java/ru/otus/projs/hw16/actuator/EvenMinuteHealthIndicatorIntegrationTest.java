package ru.otus.projs.hw16.actuator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@SpringBootTest(properties = "server.port=8080", webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class EvenMinuteHealthIndicatorIntegrationTest {

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String URL = "http://localhost:8080/actuator/health";

    @Test
    void callCustomHealth_OK() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>( headers);

        restTemplate.setErrorHandler(new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {
                return true;
            }

            @Override
            public void handleError(ClientHttpResponse response) throws IOException {

            }
        });
        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, entity, String.class);
        Assertions.assertThat(response).isNotNull();
        String healthBody = response.getBody();
        Assertions.assertThat(healthBody).isNotNull();
        Assertions.assertThat(healthBody).contains("\"status\":\"UP\"","\"alarm\"", "работает!");
    }

}