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
public class SellerFilter {
    private String searchByName;
    private List<String> producerIds;
    private List<String> marketplaceIds;
}