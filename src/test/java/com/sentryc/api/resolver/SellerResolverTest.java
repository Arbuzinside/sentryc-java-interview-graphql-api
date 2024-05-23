package com.sentryc.api.resolver;

import com.sentryc.api.model.Producer;
import com.sentryc.api.model.Seller;
import com.sentryc.api.model.SellerInfo;
import com.sentryc.api.service.SellerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class SellerResolverTest {

    @Mock
    private SellerService sellerService;

    @InjectMocks
    private SellerResolver sellerResolver;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSellersByState() {
        Seller seller = Seller.builder()
                .id(UUID.randomUUID())
                .state("REGULAR")
                .build();

        when(sellerService.getSellersByState(anyString())).thenReturn(Arrays.asList(seller));

        List<Seller> sellers = sellerResolver.sellersByState("REGULAR");
        assertEquals(1, sellers.size());
        assertEquals("REGULAR", sellers.get(0).getState());
    }

    @Test
    public void testSellersByProducerId() {
        UUID producerId = UUID.randomUUID();
        Producer producer = Producer.builder().id(producerId).build();
        Seller seller = Seller.builder()
                .id(UUID.randomUUID())
                .producer(producer)
                .build();

        when(sellerService.getSellersByProducerId(any(UUID.class))).thenReturn(Arrays.asList(seller));

        List<Seller> sellers = sellerResolver.sellersByProducerId(producerId);
        assertEquals(1, sellers.size());
        assertEquals(producerId, sellers.get(0).getProducer().getId());
    }

    @Test
    public void testSellersBySellerInfoId() {
        UUID sellerInfoId = UUID.randomUUID();
        SellerInfo sellerInfo = SellerInfo.builder().id(sellerInfoId).build();
        Seller seller = Seller.builder()
                .id(UUID.randomUUID())
                .sellerInfo(sellerInfo)
                .build();

        when(sellerService.getSellersBySellerInfoId(any(UUID.class))).thenReturn(Arrays.asList(seller));

        List<Seller> sellers = sellerResolver.sellersBySellerInfoId(sellerInfoId);
        assertEquals(1, sellers.size());
        assertEquals(sellerInfoId, sellers.get(0).getSellerInfo().getId());
    }

    @Test
    public void testSellersByProducerIdAndState() {
        UUID producerId = UUID.randomUUID();
        Producer producer = Producer.builder().id(producerId).build();
        Seller seller = Seller.builder()
                .id(UUID.randomUUID())
                .producer(producer)
                .state("REGULAR")
                .build();

        when(sellerService.getSellersByProducerIdAndState(any(UUID.class), anyString())).thenReturn(Arrays.asList(seller));

        List<Seller> sellers = sellerResolver.sellersByProducerIdAndState(producerId, "REGULAR");
        assertEquals(1, sellers.size());
        assertEquals(producerId, sellers.get(0).getProducer().getId());
        assertEquals("REGULAR", sellers.get(0).getState());
    }
}