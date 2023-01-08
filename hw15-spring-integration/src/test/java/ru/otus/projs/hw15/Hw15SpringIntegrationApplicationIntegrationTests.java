package ru.otus.projs.hw15;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import ru.otus.projs.hw15.model.Contract;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Hw15SpringIntegrationApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(properties = "server.port=8080")
class Hw15SpringIntegrationApplicationIntegrationTests {

	private final RestTemplate restTemplate = new RestTemplate();

	private final String url = "http://localhost:8080/contract/draft";

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

		ResponseEntity<Contract> response = restTemplate.exchange(url, HttpMethod.POST, entity, Contract.class);
		Assertions.assertThat(response).isNotNull();
		Contract contract = response.getBody();
		Assertions.assertThat(contract).isNotNull();
		Assertions.assertThat(contract).extracting("owner").isNotNull();
		Assertions.assertThat(contract).extracting("rule").isNotNull();
		Assertions.assertThat(contract).extracting("attachedDocument").isNotNull();
		Assertions.assertThat(contract).extracting("series").isEqualTo("DDD");

	}


}
