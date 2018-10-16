package com.gilpereda.bddpactmicroservices.offersservice.domain;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OfferFactory {

    public static List<Offer> getOffers(long productId, int count) {
        return IntStream.range(1, count + 1)
            .boxed()
            .map(i -> getOffer(i, productId))
            .collect(Collectors.toList());
    }

    private static Offer getOffer(long offerId, long productId) {
        Offer offer = new Offer();
        offer.setId(offerId);
        offer.setProductId(productId);
        offer.setShopId(offerId);
        offer.setShopName("Shop " + offerId);
        return offer;
    }
}
