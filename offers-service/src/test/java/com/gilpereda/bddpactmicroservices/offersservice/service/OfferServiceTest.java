package com.gilpereda.bddpactmicroservices.offersservice.service;

import com.gilpereda.bddpactmicroservices.offersservice.domain.Offer;
import com.gilpereda.bddpactmicroservices.offersservice.persistence.OfferRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class OfferServiceTest {

    @Mock
    private OfferRepository offerRepository;
    @InjectMocks
    private OfferService offerService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnTheOffersOfAProduct() {
        long productId = 1;
        List<Offer> offers = IntStream.range(1, 8)
            .boxed()
            .map(i -> {
                Offer offer = new Offer();
                offer.setId(i);
                offer.setProductId(productId);
                offer.setShopId(i);
                offer.setShopName("Shop " + i);
                return offer;
            })
            .collect(Collectors.toList());

        when(offerRepository.findAllByProductId(productId)).thenReturn(offers);
        assertThat(offerService.findAllTheOffersOfAProduct(productId)).isEqualTo(offers);
    }

}
