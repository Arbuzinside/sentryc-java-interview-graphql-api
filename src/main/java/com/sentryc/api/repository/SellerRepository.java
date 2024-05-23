package com.sentryc.api.repository;

import com.sentryc.api.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface SellerRepository extends JpaRepository<Seller, UUID> {

    // Query method to find sellers by state
    List<Seller> findByState(String state);

    // Query method to find sellers by producer ID
    List<Seller> findByProducerId(UUID producerId);

    // Query method to find sellers by seller info ID
    List<Seller> findBySellerInfoId(UUID sellerInfoId);

    // Query method to find sellers by producer ID and state
    List<Seller> findByProducerIdAndState(UUID producerId, String state);
}

