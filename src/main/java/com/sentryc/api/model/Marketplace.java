package com.sentryc.api.model;

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
public class Marketplace {
    @Id
    private String id;
    private String description;
}
