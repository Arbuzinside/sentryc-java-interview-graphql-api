package com.sentryc.api.service;

import com.sentryc.api.model.Seller;
import com.sentryc.api.repository.SellerRepository;
import com.sentryc.api.resolver.model.PageInput;
import com.sentryc.api.resolver.model.SellerFilter;
import com.sentryc.api.resolver.model.SellerSortBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    public Page<Seller> getSellers(SellerFilter filter, PageInput page, SellerSortBy sortBy) {
        Pageable pageable = PageRequest.of(page.getPage(), page.getSize(), getSort(sortBy));
        return sellerRepository.findAll(pageable);
    }

    private Sort getSort(SellerSortBy sortBy) {
        return switch (sortBy) {
            case SELLER_INFO_EXTERNAL_ID_ASC -> Sort.by(Sort.Direction.ASC, "sellerInfo.externalId");
            case SELLER_INFO_EXTERNAL_ID_DESC -> Sort.by(Sort.Direction.DESC, "sellerInfo.externalId");
            case NAME_ASC -> Sort.by(Sort.Direction.ASC, "sellerInfo.name");
            case NAME_DESC -> Sort.by(Sort.Direction.DESC, "sellerInfo.name");
            case MARKETPLACE_ID_ASC -> Sort.by(Sort.Direction.ASC, "sellerInfo.marketplace.id");
            case MARKETPLACE_ID_DESC -> Sort.by(Sort.Direction.DESC, "sellerInfo.marketplace.id");
            default -> Sort.unsorted();
        };
    }
}