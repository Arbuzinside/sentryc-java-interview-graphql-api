package com.sentryc.api.resolver;

import com.sentryc.api.model.dto.PageInput;
import com.sentryc.api.model.dto.SellerFilter;
import com.sentryc.api.model.dto.SellerPageableResponse;
import com.sentryc.api.model.dto.SellerSortBy;
import com.sentryc.api.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;


@Controller
public class SellerResolver {

    private final SellerService sellerService;

    @Autowired
    public SellerResolver(SellerService sellerService){
        this.sellerService = sellerService;
    }

    @QueryMapping
    public SellerPageableResponse sellers(@Argument SellerFilter filter,
                                          @Argument PageInput page,
                                          @Argument SellerSortBy sortBy) {
        if (filter == null) {
            filter = new SellerFilter();
        }
        if (page == null) {
            page = new PageInput(0, Integer.MAX_VALUE);
        }
        if (sortBy == null) {
            sortBy = SellerSortBy.UNSORTED;
        }

        return sellerService.getSellers(filter, page, sortBy);
    }
}