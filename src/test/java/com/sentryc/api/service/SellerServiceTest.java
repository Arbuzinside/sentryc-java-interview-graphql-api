package com.sentryc.api.service;

import com.sentryc.api.model.Marketplace;
import com.sentryc.api.model.Producer;
import com.sentryc.api.model.Seller;
import com.sentryc.api.model.SellerInfo;
import com.sentryc.api.repository.SellerRepository;
import com.sentryc.api.resolver.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;

class SellerServiceTest {

    private SellerService sellerService;

    @Mock
    private SellerRepository sellerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sellerService = new SellerService(sellerRepository);
    }

    @Test
    void givenValidInputs_whenGetSellers_thenReturnsSellerPageableResponse() {
        // given
        var filter = new SellerFilter();
        var page = new PageInput(0, 10);
        var sortBy = SellerSortBy.NAME_ASC;
        var seller = initTestSeller();

        Page<Seller> sellerPage = new PageImpl<>(List.of(seller), PageRequest.of(0, 10), 1);
        given(sellerRepository.findAll(any(Specification.class), any(Pageable.class))).willReturn(sellerPage);

        // when
        SellerPageableResponse response = sellerService.getSellers(filter, page, sortBy);

        // then
        assertEquals(1, response.getData().size());
        assertEquals(1, response.getMeta().getTotalElements());
    }

    @Test
    void givenSellersWithSameInfo_whenGetSellers_thenGroupsAndMergesCorrectly() {
        // given
        var filter = new SellerFilter();
        var page = new PageInput(0, 10);
        var sortBy = SellerSortBy.NAME_ASC;

        // Create sellerInfo to be shared by multiple sellers
        var sharedSellerInfo = SellerInfo.builder()
                .id(UUID.randomUUID())
                .url("test.com")
                .name("testSeller")
                .country("testCountry")
                .externalId(UUID.randomUUID().toString())
                .marketplace(Marketplace.builder().id(UUID.randomUUID().toString()).build())
                .build();

        // Create multiple sellers with the same sellerInfo but different producers
        var producer1 = Producer.builder().id(UUID.randomUUID()).createdAt(LocalDateTime.now()).name("producer1").build();
        var seller1 = initTestSeller(sharedSellerInfo, producer1);

        var producer2 = Producer.builder().id(UUID.randomUUID()).createdAt(LocalDateTime.now()).name("producer2").build();
        var seller2 = initTestSeller(sharedSellerInfo, producer2);

        Page<Seller> sellerPage = new PageImpl<>(List.of(seller1, seller2), PageRequest.of(0, 10), 2);
        given(sellerRepository.findAll(any(Specification.class), any(Pageable.class))).willReturn(sellerPage);

        // when
        SellerPageableResponse response = sellerService.getSellers(filter, page, sortBy);

        // then
        assertEquals(1, response.getData().size());
        var sellerDto = response.getData().getFirst();
        assertEquals(2, sellerDto.getProducerSellerStates().size());
    }

    private Seller initTestSeller() {
        return Seller.builder()
                .id(UUID.randomUUID())
                .sellerInfo(SellerInfo.builder()
                        .id(UUID.randomUUID())
                        .url("test.com")
                        .name("testSeller")
                        .country("testCountry")
                        .externalId(UUID.randomUUID().toString())
                        .marketplace(Marketplace.builder()
                                .id(UUID.randomUUID().toString())
                                .build())
                        .build())
                .producer(Producer.builder()
                        .id(UUID.randomUUID())
                        .createdAt(LocalDateTime.now())
                        .name("testProducerName")
                        .build())
                .state("REGULAR")
                .build();
    }

    private Seller initTestSeller(SellerInfo sellerInfo, Producer producer) {
        return Seller.builder()
                .id(UUID.randomUUID())
                .sellerInfo(sellerInfo)
                .producer(producer)
                .state("REGULAR")
                .build();
    }
}