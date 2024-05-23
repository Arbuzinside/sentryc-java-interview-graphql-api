package com.sentryc.api.service;

import com.sentryc.api.model.Seller;
import com.sentryc.api.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    public List<Seller> getSellersByState(String state) {
        return sellerRepository.findByState(state);
    }

    public List<Seller> getSellersByProducerId(UUID producerId) {
        return sellerRepository.findByProducerId(producerId);
    }

    public List<Seller> getSellersBySellerInfoId(UUID sellerInfoId) {
        return sellerRepository.findBySellerInfoId(sellerInfoId);
    }

    public List<Seller> getSellersByProducerIdAndState(UUID producerId, String state) {
        return sellerRepository.findByProducerIdAndState(producerId, state);
    }
}

