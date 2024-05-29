package com.sentryc.api.model.dto;

import com.sentryc.api.model.entity.SellerEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProducerSellerState {
    private String producerId;
    private String producerName;
    private SellerState sellerState;
    private String sellerId;

    public static ProducerSellerState from(SellerEntity seller){
        return ProducerSellerState.builder()
                .producerId(seller.getProducer().getId().toString())
                .producerName(seller.getProducer().getName())
                .sellerState(SellerState.valueOf(seller.getState()))
                .sellerId(seller.getId().toString())
                .build();

    }
}
