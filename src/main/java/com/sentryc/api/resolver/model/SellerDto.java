package com.sentryc.api.resolver.model;

import com.sentryc.api.model.Seller;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SellerDto {
    private String sellerName;
    private String externalId;
    private List<ProducerSellerStateDto> producerSellerStates;
    private String marketplaceId;

    public static SellerDto from(Seller seller, List<ProducerSellerStateDto> producerSellerStates){
        return SellerDto.builder()
                .sellerName(seller.getSellerInfo().getName())
                .externalId(seller.getSellerInfo().getExternalId())
                .producerSellerStates(producerSellerStates)
                .marketplaceId(seller.getSellerInfo().getMarketplace().getId())
                .build();
    }
}
