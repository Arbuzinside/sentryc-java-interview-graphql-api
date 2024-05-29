package com.sentryc.api.service;

import com.sentryc.api.model.entity.SellerEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class SellerSpecifications {

    public static Specification<SellerEntity> searchByName(String searchByName) {
        return (root, query, criteriaBuilder) -> {
            if (StringUtils.hasText(searchByName)) {
                return criteriaBuilder.like(root.get("sellerInfo").get("name"), "%" + searchByName + "%");
            }
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<SellerEntity> filterByProducerIds(List<String> producerIds) {
        return (root, query, criteriaBuilder) -> {
            if (producerIds != null && !producerIds.isEmpty()) {
                return root.get("producer").get("id").in(producerIds);
            }
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<SellerEntity> filterByMarketplaceIds(List<String> marketplaceIds) {
        return (root, query, criteriaBuilder) -> {
            if (marketplaceIds != null && !marketplaceIds.isEmpty()) {
                return root.get("sellerInfo").get("marketplace").get("id").in(marketplaceIds);
            }
            return criteriaBuilder.conjunction();
        };
    }
}