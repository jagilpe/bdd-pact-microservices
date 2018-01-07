package com.gilpereda.bddpactmicroservices.offersservice.controller;

import com.gilpereda.bddpactmicroservices.offersservice.domain.Offer;
import com.gilpereda.bddpactmicroservices.offersservice.domain.OfferFactory;
import com.gilpereda.bddpactmicroservices.offersservice.service.OfferService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class OfferControllerTest {

    @Mock
    private OfferService offerService;
    @InjectMocks
    private OfferController offerController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnTheOffersOfAProduct() {
        long productId = 1;
        List<Offer> offers = OfferFactory.getOffers(productId, 5);

        when(offerService.findAllTheOffersOfAProduct(productId)).thenReturn(offers);
        assertThat(offerController.getProductOffers(productId)).isEqualTo(offers);
    }

}
