package com.sentryc.api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "seller_infos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "marketplace_id")
    private Marketplace marketplace;

    private String name;
    private String url;
    private String country;
    private String externalId;
}
