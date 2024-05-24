package com.sentryc.api.repository;

import com.sentryc.api.model.Seller;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class SellerSpecifications {

    public static Specification<Seller> searchByName(String searchByName) {
        return (root, query, criteriaBuilder) -> {
            if (StringUtils.hasText(searchByName)) {
                return criteriaBuilder.like(root.get("sellerInfo").get("name"), "%" + searchByName + "%");
            }
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<Seller> filterByProducerIds(List<String> producerIds) {
        return (root, query, criteriaBuilder) -> {
            if (producerIds != null && !producerIds.isEmpty()) {
                return root.get("producer").get("id").in(producerIds);
            }
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<Seller> filterByMarketplaceIds(List<String> marketplaceIds) {
        return (root, query, criteriaBuilder) -> {
            if (marketplaceIds != null && !marketplaceIds.isEmpty()) {
                return root.get("sellerInfo").get("marketplace").get("id").in(marketplaceIds);
            }
            return criteriaBuilder.conjunction();
        };
    }
}