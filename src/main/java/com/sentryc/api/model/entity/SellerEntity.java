package com.sentryc.api.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "sellers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "producer_id")
    private ProducerEntity producer;

    @ManyToOne
    @JoinColumn(name = "seller_info_id")
    private SellerInfoEntity sellerInfo;

    private String state;
}