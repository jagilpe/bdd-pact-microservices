import { ProductOffer } from '../domain/product-offer';
import { inject, TestBed } from '@angular/core/testing';
import { RestProductOfferRepository } from './rest-product-offer.repository';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { BACKEND_URL } from '../app.parameters';

describe('The Offer Service', () => {

  const productId: number = 1;
  const offers: ProductOffer[] = [
    { productId: productId, shopId: 1, shopName: 'Shop 1', price: 500 },
    { productId: productId, shopId: 2, shopName: 'Shop 2', price: 525 },
    { productId: productId, shopId: 3, shopName: 'Shop 3', price: 450 },
    { productId: productId, shopId: 4, shopName: 'Shop 4', price: 475 },
  ];

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        RestProductOfferRepository,
        { provide: BACKEND_URL, useValue: '' }
      ],
      imports: [ HttpClientTestingModule ]
    });
  });

  it('should return the offers of a product', inject(
    [RestProductOfferRepository, HttpTestingController],
    (productOfferService: RestProductOfferRepository, httpMock: HttpTestingController) => {
      productOfferService.findAllByProductId(productId)
        .subscribe(actualOffers => expect(actualOffers).toEqual(offers));

      const request = httpMock.expectOne(`/offers/product/${productId}`);
      expect(request.request.method).toEqual('GET');

      request.flush(offers);

      httpMock.verify();
    }));
});
