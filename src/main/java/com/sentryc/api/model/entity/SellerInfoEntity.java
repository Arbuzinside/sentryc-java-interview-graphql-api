package com.sentryc.api.model.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "seller_infos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellerInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "marketplace_id")
    private MarketplaceEntity marketplace;

    private String name;
    private String url;
    private String country;
    private String externalId;
}
