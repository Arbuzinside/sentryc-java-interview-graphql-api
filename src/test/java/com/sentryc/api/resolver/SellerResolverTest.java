package com.sentryc.api.resolver;

import com.sentryc.api.model.dto.PageInput;
import com.sentryc.api.model.dto.SellerFilter;
import com.sentryc.api.model.dto.SellerPageableResponse;
import com.sentryc.api.model.dto.SellerSortBy;
import com.sentryc.api.service.SellerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class SellerResolverTest {

    private SellerResolver sellerResolver;

    @Mock
    private SellerService sellerService;

    @Captor
    private ArgumentCaptor<SellerFilter> filterCaptor;

    @Captor
    private ArgumentCaptor<PageInput> pageCaptor;

    @Captor
    private ArgumentCaptor<SellerSortBy> sortByCaptor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        sellerResolver = new SellerResolver(sellerService);
    }

    @Test
    void givenNullArguments_whenSellers_thenUsesDefaultValues() {
        // given
        var defaultFilter = new SellerFilter();
        var defaultPage = new PageInput(0, Integer.MAX_VALUE);
        var defaultSortBy = SellerSortBy.UNSORTED;
        var expectedResponse = new SellerPageableResponse();

        given(sellerService.getSellers(any(SellerFilter.class), any(PageInput.class), any(SellerSortBy.class)))
                .willReturn(expectedResponse);

        // when
        SellerPageableResponse response = sellerResolver.sellers(null, null, null);

        // then
        assertEquals(expectedResponse, response);

        // Verify that the default values were used
        verify(sellerService).getSellers(filterCaptor.capture(), pageCaptor.capture(), sortByCaptor.capture());
        assertEquals(defaultFilter, filterCaptor.getValue());
        assertEquals(defaultPage, pageCaptor.getValue());
        assertEquals(defaultSortBy, sortByCaptor.getValue());
    }

    @Test
    void givenValidArguments_whenSellers_thenReturnsExpectedResponse() {
        // given
        SellerFilter filter = new SellerFilter();
        PageInput page = new PageInput(0, 10);
        SellerSortBy sortBy = SellerSortBy.NAME_ASC;
        SellerPageableResponse expectedResponse = new SellerPageableResponse();

        given(sellerService.getSellers(filter, page, sortBy)).willReturn(expectedResponse);

        // when
        SellerPageableResponse response = sellerResolver.sellers(filter, page, sortBy);

        // then
        assertEquals(expectedResponse, response);

        // Verify that the provided values were used
        verify(sellerService).getSellers(filterCaptor.capture(), pageCaptor.capture(), sortByCaptor.capture());
        assertEquals(filter, filterCaptor.getValue());
        assertEquals(page, pageCaptor.getValue());
        assertEquals(sortBy, sortByCaptor.getValue());
    }
}