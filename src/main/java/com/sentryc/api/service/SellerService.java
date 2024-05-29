package com.sentryc.api.service;

import com.sentryc.api.model.Seller;
import com.sentryc.api.repository.SellerRepository;
import com.sentryc.api.resolver.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class SellerService {

    private final SellerRepository sellerRepository;

    @Autowired
    public SellerService(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    public SellerPageableResponse getSellers(SellerFilter filter, PageInput page, SellerSortBy sortBy) {
        var pageable = PageRequest.of(page.getPage(), page.getSize(), getSort(sortBy));
        Specification<Seller> specs = createQuerySpecification(filter);

        Page<Seller> sellerPage = sellerRepository.findAll(specs, pageable);

        var pageMeta = PageMeta.from(sellerPage);

        var sellerDtos = sellerPage
                .getContent()
                .stream()
                // group sellers by name and externalId
                .collect(Collectors.groupingBy(s -> s.getSellerInfo().getName() + s.getSellerInfo().getExternalId()))
                .values()
                .stream()
                .map(this::mergeProducerSellerStates)
                .toList();

        return SellerPageableResponse.builder()
                .meta(pageMeta)
                .data(sellerDtos)
                .build();
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

    private Specification<Seller> createQuerySpecification(SellerFilter filter) {
        return Specification.where(SellerSpecifications.searchByName(filter.getSearchByName()))
                .and(SellerSpecifications.filterByProducerIds(filter.getProducerIds()))
                .and(SellerSpecifications.filterByMarketplaceIds(filter.getMarketplaceIds()));
    }

    private SellerDto mergeProducerSellerStates(List<Seller> sellers) {
        Seller mainEntity = sellers.getFirst();

        List<ProducerSellerStateDto> allStates = sellers.stream()
                .map(ProducerSellerStateDto::from)
                .collect(Collectors.toList());

        return SellerDto.from(mainEntity, allStates);
    }
}