package com.gilpereda.bddpactmicroservices.offersservice.controller;

import com.gilpereda.bddpactmicroservices.offersservice.domain.Offer;
import com.gilpereda.bddpactmicroservices.offersservice.persistence.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OfferController {

    @Autowired
    private OfferRepository offerRepository;

    @RequestMapping(path = "/product/{product}", method = RequestMethod.GET)
    public Iterable<Offer> getProductOffers(@PathVariable("product") final long productId) {
        return offerRepository.findAllByProductId(productId);
    }
}
