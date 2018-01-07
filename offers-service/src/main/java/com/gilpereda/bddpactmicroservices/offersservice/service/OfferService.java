package com.gilpereda.bddpactmicroservices.offersservice.service;

import com.gilpereda.bddpactmicroservices.offersservice.domain.Offer;
import com.gilpereda.bddpactmicroservices.offersservice.persistence.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfferService {

    @Autowired
    private OfferRepository offerRepository;

    public Iterable<Offer> findAllTheOffersOfAProduct(final long productId) {
        return offerRepository.findAllByProductId(productId);
    }
}
