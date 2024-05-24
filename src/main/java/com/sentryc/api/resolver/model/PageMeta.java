package com.sentryc.api.resolver.model;

import com.sentryc.api.model.Seller;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageMeta {
    private int currentPage;
    private int pageSize;
    private int totalPages;
    private long totalElements;

    public static PageMeta from(Page<Seller> page) {
        return PageMeta.builder()
                .currentPage(page.getNumber())
                .pageSize(page.getSize())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .build();
    }
}

