package com.sentryc.api.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "marketplaces")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketplaceEntity {
    @Id
    private String id;
    private String description;
}
