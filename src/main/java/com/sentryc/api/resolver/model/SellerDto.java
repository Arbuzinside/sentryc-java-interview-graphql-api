package com.sentryc.api.resolver.model;

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
}
