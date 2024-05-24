package com.sentryc.api.resolver.model;

import com.sentryc.api.model.Producer;
import com.sentryc.api.model.Seller;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProducerSellerStateDto {
    private String producerId;
    private String producerName;
    private SellerState sellerState;
    private String sellerId;

    public static ProducerSellerStateDto from(Seller seller){
        return ProducerSellerStateDto.builder()
                .producerId(seller.getProducer().getId().toString())
                .producerName(seller.getProducer().getName())
                .sellerState(SellerState.valueOf(seller.getState()))
                .sellerId(seller.getId().toString())
                .build();

    }
}
