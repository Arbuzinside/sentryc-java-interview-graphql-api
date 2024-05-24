package com.sentryc.api.resolver;

import com.sentryc.api.service.SellerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;


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
}