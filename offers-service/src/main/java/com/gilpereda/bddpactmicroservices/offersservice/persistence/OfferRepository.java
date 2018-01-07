package com.gilpereda.bddpactmicroservices.offersservice.persistence;

import com.gilpereda.bddpactmicroservices.offersservice.domain.Offer;
import org.springframework.data.repository.CrudRepository;

public interface OfferRepository extends CrudRepository<Offer, Long> {

    Iterable<Offer> findAllByProductId(long productId);

}
