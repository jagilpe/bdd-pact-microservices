package com.gilpereda.bddpactmicroservices.offersservice.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gilpereda.bddpactmicroservices.offersservice.domain.Offer;
import com.gilpereda.bddpactmicroservices.offersservice.service.OfferService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OfferController {

    private final OfferService offerService;

    @RequestMapping(path = "/product/{product}", method = RequestMethod.GET)
    public Iterable<Offer> getProductOffers(@PathVariable("product") long productId) {
        return offerService.findAllTheOffersOfAProduct(productId);
    }
}
