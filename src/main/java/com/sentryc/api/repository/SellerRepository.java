package com.sentryc.api.repository;

import com.sentryc.api.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SellerRepository extends JpaRepository<Seller, UUID> {
}

