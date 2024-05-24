package com.sentryc.api.resolver.model;

import com.sentryc.api.model.Seller;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SellerPageableResponse {
    private PageMeta meta;
    private List<SellerDto> data;

    public static SellerPageableResponse fromSellersPage(Page<Seller> sellerEntitiesPage){
        var data = sellerEntitiesPage.getContent().stream()
                    .map(seller -> SellerDto.builder().
                        sellerName(seller.getSellerInfo().getName())
                        .externalId(seller.getSellerInfo().getExternalId())
                        //TODO: map here to many
                        .producerSellerStates(List.of(ProducerSellerStateDto.from(seller, seller.getProducer())))
                        .marketplaceId(seller.getSellerInfo().getMarketplace().getId())
                        .build())
                .collect(Collectors.toList());

         var meta = new PageMeta(sellerEntitiesPage.getTotalPages(), sellerEntitiesPage.getTotalElements());

         return SellerPageableResponse.builder()
                 .data(data)
                 .meta(meta)
                 .build();
    }
}