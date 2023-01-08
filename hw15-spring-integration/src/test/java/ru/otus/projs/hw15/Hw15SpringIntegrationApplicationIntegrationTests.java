package ru.otus.projs.hw15;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.otus.projs.hw15.model.Contract;

@SpringBootTest(properties = "server.port=8080", webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class Hw15SpringIntegrationApplicationIntegrationTests {

	private final RestTemplate restTemplate = new RestTemplate();

	private final static String URL = "http://localhost:8080/contract/draft";

	@Test
	void callContractService_OK() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		String request = "{\n" +
				"    \"customer\": {\"name\": \"Name1\", \"lastName\": \"LastName1\"}, \n" +
				"    \"customerDocumentType\": \"права\", \n" +
				"    \"rule\": {\"name\": \"СУПЕР_ДЕПОЗИТ\"}\n" +
				"}";
		HttpEntity<String> entity = new HttpEntity<>(request, headers);

		ResponseEntity<Contract> response = restTemplate.exchange(URL, HttpMethod.POST, entity, Contract.class);
		Assertions.assertThat(response).isNotNull();
		Contract contract = response.getBody();
		Assertions.assertThat(contract).isNotNull();
		Assertions.assertThat(contract).extracting("owner").isNotNull();
		Assertions.assertThat(contract).extracting("rule").isNotNull();
		Assertions.assertThat(contract).extracting("attachedDocument").isNotNull();
		Assertions.assertThat(contract).extracting("series").isEqualTo("DDD");

	}


}
