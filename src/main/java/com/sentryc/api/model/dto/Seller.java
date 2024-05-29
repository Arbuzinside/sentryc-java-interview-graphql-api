package com.sentryc.api.model.dto;

import com.sentryc.api.model.entity.SellerEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seller {
    private String sellerName;
    private String externalId;
    private List<ProducerSellerState> producerSellerStates;
    private String marketplaceId;

    public static Seller from(SellerEntity seller, List<ProducerSellerState> producerSellerStates){
        return Seller.builder()
                .sellerName(seller.getSellerInfo().getName())
                .externalId(seller.getSellerInfo().getExternalId())
                .producerSellerStates(producerSellerStates)
                .marketplaceId(seller.getSellerInfo().getMarketplace().getId())
                .build();
    }
}
