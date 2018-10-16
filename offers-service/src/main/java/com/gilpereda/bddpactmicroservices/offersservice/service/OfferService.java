package com.gilpereda.bddpactmicroservices.offersservice.service;

import org.springframework.stereotype.Service;

import com.gilpereda.bddpactmicroservices.offersservice.domain.Offer;
import com.gilpereda.bddpactmicroservices.offersservice.persistence.OfferRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;

    public Iterable<Offer> findAllTheOffersOfAProduct(final long productId) {
        return offerRepository.findAllByProductId(productId);
    }
}
