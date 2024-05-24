package com.sentryc.api.resolver.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SellerPageableResponse {
    private PageMeta meta;
    private List<SellerDto> data;
}