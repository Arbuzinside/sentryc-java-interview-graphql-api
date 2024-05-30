package com.sentryc.api;

import com.jayway.jsonpath.JsonPath;
import com.sentryc.api.repository.SellerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(scripts = {"/test-schema.sql", "/test-data.sql"})
public class SellerResolverIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private SellerRepository sellerRepository;

    @BeforeEach
    void setup() {
        // Runs before each test, ensuring the database is in a known state
    }

    @Test
    void givenValidQuery_whenGetSellers_thenReturnsSellerPageableResponse() {
        HttpEntity<String> entity = getStringHttpEntity();

        ResponseEntity<String> response = restTemplate.exchange("/graphql", HttpMethod.POST, entity, String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotEmpty();

        String responseBody = response.getBody();

        // Validate meta information
        int currentPage = JsonPath.parse(responseBody).read("$.data.sellers.meta.currentPage", Integer.class);
        int pageSize = JsonPath.parse(responseBody).read("$.data.sellers.meta.pageSize", Integer.class);
        int totalPages = JsonPath.parse(responseBody).read("$.data.sellers.meta.totalPages", Integer.class);
        int totalElements = JsonPath.parse(responseBody).read("$.data.sellers.meta.totalElements", Integer.class);

        assertThat(currentPage).isEqualTo(0);
        assertThat(pageSize).isEqualTo(10);
        assertThat(totalPages).isEqualTo(1);
        // amount of sellers
        assertThat(totalElements).isEqualTo(3);
    }

    private static HttpEntity<String> getStringHttpEntity() {
        var query = "{ \"query\": \"{ sellers(filter: null, page: {page: 0, size: 10}, sortBy: NAME_ASC) { meta { currentPage pageSize totalPages totalElements } data { sellerName externalId producerSellerStates { producerId producerName sellerState sellerId } marketplaceId } } }\" }";
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("user", "password");
        headers.add("Content-Type", "application/json");
        return new HttpEntity<>(query, headers);
    }

    @Test
    void givenSellerNameFilter_whenGetSellers_thenReturnsOnlyMatchingSellers() {
        // filter by "seller A"
        String query = "{ \"query\": \"{ sellers(filter: { searchByName: \\\"Seller A\\\" }, page: { page: 0, size: 10 }, sortBy: NAME_ASC) { meta { currentPage pageSize totalPages totalElements } data { sellerName externalId producerSellerStates { producerId producerName sellerState sellerId } marketplaceId } } }\" }";

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("user", "password");
        headers.add("Content-Type", "application/json");
        HttpEntity<String> requestEntity = new HttpEntity<>(query, headers);

        ResponseEntity<String> response = restTemplate.exchange("/graphql", HttpMethod.POST, requestEntity, String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotEmpty();

        // Parse the JSON response
        String responseBody = response.getBody();

        // Validate meta information
        int totalElements = JsonPath.parse(responseBody).read("$.data.sellers.meta.totalElements", Integer.class);
        assertThat(totalElements).isEqualTo(2);
    }

    @Test
    void givenSellerNameFilter_whenSellersDoNotMatchMatch_thenReturnsEmptyList() {
        // filter by "seller A"
        String query = "{ \"query\": \"{ sellers(filter: { searchByName: \\\"Seller C\\\" }, page: { page: 0, size: 10 }, sortBy: NAME_ASC) { meta { currentPage pageSize totalPages totalElements } data { sellerName externalId producerSellerStates { producerId producerName sellerState sellerId } marketplaceId } } }\" }";

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("user", "password");
        headers.add("Content-Type", "application/json");
        HttpEntity<String> requestEntity = new HttpEntity<>(query, headers);

        ResponseEntity<String> response = restTemplate.exchange("/graphql", HttpMethod.POST, requestEntity, String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotEmpty();

        // Parse the JSON response
        String responseBody = response.getBody();

        // Validate meta information
        int totalElements = JsonPath.parse(responseBody).read("$.data.sellers.meta.totalElements", Integer.class);
        assertThat(totalElements).isEqualTo(0);
    }

    @Test
    void givenMarketplaceIdsFilter_whenGetSellers_thenReturnsOnlyMatchingSellers() {
        String query = "{ \"query\": \"{ sellers(filter: { marketplaceIds: [\\\"mkt-001\\\"] }, page: { page: 0, size: 10 }, sortBy: NAME_ASC) { meta { currentPage pageSize totalPages totalElements } data { sellerName externalId producerSellerStates { producerId producerName sellerState sellerId } marketplaceId } } }\" }";

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("user", "password");
        headers.add("Content-Type", "application/json");
        HttpEntity<String> requestEntity = new HttpEntity<>(query, headers);

        ResponseEntity<String> response = restTemplate.exchange("/graphql", HttpMethod.POST, requestEntity, String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotEmpty();

        // Parse the JSON response
        String responseBody = response.getBody();

        // Validate meta information
        int totalElements = JsonPath.parse(responseBody).read("$.data.sellers.meta.totalElements", Integer.class);
        assertThat(totalElements).isEqualTo(3);
    }

    @Test
    void givenSortByNameDesc_whenGetSellers_thenReturnsSellersSortedByNameDesc() {
        String query = "{ \"query\": \"{ sellers(filter: null, page: { page: 0, size: 10 }, sortBy: NAME_DESC) { meta { currentPage pageSize totalPages totalElements } data { sellerName externalId } } }\" }";

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("user", "password");
        headers.add("Content-Type", "application/json");
        HttpEntity<String> requestEntity = new HttpEntity<>(query, headers);

        ResponseEntity<String> response = restTemplate.exchange("/graphql", HttpMethod.POST, requestEntity, String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotEmpty();

        // Parse the JSON response
        String responseBody = response.getBody();

        // Validate data order
        List<String> sellerNames = JsonPath.parse(responseBody).read("$.data.sellers.data[*].sellerName");

        assertThat(sellerNames).isSortedAccordingTo(Comparator.reverseOrder());
    }
}