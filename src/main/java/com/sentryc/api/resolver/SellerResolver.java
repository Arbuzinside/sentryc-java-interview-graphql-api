package com.sentryc.api.resolver;

import com.sentryc.api.model.Seller;
import com.sentryc.api.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class SellerResolver {

    @Autowired
    private SellerService sellerService;

    @QueryMapping
    public List<Seller> sellersByState(@Argument String state) {
        return sellerService.getSellersByState(state);
    }

    @QueryMapping
    public List<Seller> sellersByProducerId(@Argument UUID producerId) {
        return sellerService.getSellersByProducerId(producerId);
    }

    @QueryMapping
    public List<Seller> sellersBySellerInfoId(@Argument UUID sellerInfoId) {
        return sellerService.getSellersBySellerInfoId(sellerInfoId);
    }

    @QueryMapping
    public List<Seller> sellersByProducerIdAndState(@Argument UUID producerId, @Argument String state) {
        return sellerService.getSellersByProducerIdAndState(producerId, state);
    }
}

