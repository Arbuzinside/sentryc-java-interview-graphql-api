package com.sentryc.api;

import com.jayway.jsonpath.JsonPath;
import com.sentryc.api.repository.SellerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(scripts = {"/test-schema.sql", "/test-data.sql"})
public class SellerResolverIntegrationTest {

    private static final String GRAPHQL_ENDPOINT = "/graphql";
    private static final String AUTH_USER = "user";
    private static final String AUTH_PASSWORD = "password";
    private static final String CONTENT_TYPE_JSON = "application/json";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private SellerRepository sellerRepository;

    @Test
    void givenValidQuery_whenGetSellers_thenReturnsSellerPageableResponse() {
        var query = "{ \"query\": \"{ sellers(filter: null, page: {page: 0, size: 10}, sortBy: NAME_ASC) { meta { currentPage pageSize totalPages totalElements } data { sellerName externalId producerSellerStates { producerId producerName sellerState sellerId } marketplaceId } } }\" }";
        var entity = createHttpEntity(query);

        var response = restTemplate.exchange(GRAPHQL_ENDPOINT, HttpMethod.POST, entity, String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotEmpty();

        var responseBody = response.getBody();

        var currentPage = JsonPath.parse(responseBody).read("$.data.sellers.meta.currentPage", Integer.class);
        var pageSize = JsonPath.parse(responseBody).read("$.data.sellers.meta.pageSize", Integer.class);
        var totalPages = JsonPath.parse(responseBody).read("$.data.sellers.meta.totalPages", Integer.class);
        var totalElements = JsonPath.parse(responseBody).read("$.data.sellers.meta.totalElements", Integer.class);

        assertThat(currentPage).isEqualTo(0);
        assertThat(pageSize).isEqualTo(10);
        assertThat(totalPages).isEqualTo(1);
        assertThat(totalElements).isEqualTo(3);
    }

    @Test
    void givenSellerNameFilter_whenGetSellers_thenReturnsOnlyMatchingSellers() {
        var query = "{ \"query\": \"{ sellers(filter: { searchByName: \\\"Seller A\\\" }, page: { page: 0, size: 10 }, sortBy: NAME_ASC) { meta { currentPage pageSize totalPages totalElements } data { sellerName externalId producerSellerStates { producerId producerName sellerState sellerId } marketplaceId } } }\" }";
        var entity = createHttpEntity(query);

        var response = restTemplate.exchange(GRAPHQL_ENDPOINT, HttpMethod.POST, entity, String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotEmpty();

        var responseBody = response.getBody();

        var totalElements = JsonPath.parse(responseBody).read("$.data.sellers.meta.totalElements", Integer.class);
        assertThat(totalElements).isEqualTo(2);
    }

    @Test
    void givenSellerNameFilter_whenSellersDoNotMatchMatch_thenReturnsEmptyList() {
        var query = "{ \"query\": \"{ sellers(filter: { searchByName: \\\"Seller C\\\" }, page: { page: 0, size: 10 }, sortBy: NAME_ASC) { meta { currentPage pageSize totalPages totalElements } data { sellerName externalId producerSellerStates { producerId producerName sellerState sellerId } marketplaceId } } }\" }";
        var entity = createHttpEntity(query);

        var response = restTemplate.exchange(GRAPHQL_ENDPOINT, HttpMethod.POST, entity, String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotEmpty();

        var responseBody = response.getBody();

        var totalElements = JsonPath.parse(responseBody).read("$.data.sellers.meta.totalElements", Integer.class);
        assertThat(totalElements).isEqualTo(0);
    }

    @Test
    void givenMarketplaceIdsFilter_whenGetSellers_thenReturnsOnlyMatchingSellers() {
        var query = "{ \"query\": \"{ sellers(filter: { marketplaceIds: [\\\"mkt-001\\\"] }, page: { page: 0, size: 10 }, sortBy: NAME_ASC) { meta { currentPage pageSize totalPages totalElements } data { sellerName externalId producerSellerStates { producerId producerName sellerState sellerId } marketplaceId } } }\" }";
        var entity = createHttpEntity(query);

        var response = restTemplate.exchange(GRAPHQL_ENDPOINT, HttpMethod.POST, entity, String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotEmpty();

        var responseBody = response.getBody();

        var totalElements = JsonPath.parse(responseBody).read("$.data.sellers.meta.totalElements", Integer.class);
        assertThat(totalElements).isEqualTo(3);
    }

    @Test
    void givenSortByNameDesc_whenGetSellers_thenReturnsSellersSortedByNameDesc() {
        var query = "{ \"query\": \"{ sellers(filter: null, page: { page: 0, size: 10 }, sortBy: NAME_DESC) { meta { currentPage pageSize totalPages totalElements } data { sellerName externalId } } }\" }";
        var entity = createHttpEntity(query);

        var response = restTemplate.exchange(GRAPHQL_ENDPOINT, HttpMethod.POST, entity, String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotEmpty();

        var responseBody = response.getBody();

        var sellerNames = JsonPath.parse(responseBody).read("$.data.sellers.data[*].sellerName", List.class);

        assertThat(sellerNames).isSortedAccordingTo(Comparator.reverseOrder());
    }

    private HttpHeaders createHeaders() {
        var headers = new HttpHeaders();
        headers.setBasicAuth(AUTH_USER, AUTH_PASSWORD);
        headers.add("Content-Type", CONTENT_TYPE_JSON);
        return headers;
    }

    private HttpEntity<String> createHttpEntity(String query) {
        return new HttpEntity<>(query, createHeaders());
    }
}

