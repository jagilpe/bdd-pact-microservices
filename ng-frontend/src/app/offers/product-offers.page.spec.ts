import { async, TestBed } from '@angular/core/testing';
import { PRODUCT_OFFER_REPOSITORY, PRODUCT_REPOSITORY } from '../app-injectable-tokens';
import { Observable } from 'rxjs/Observable';
import { ActivatedRoute, convertToParamMap, ParamMap } from '@angular/router';
import { ProductOffersPage } from './product-offers.page';
import { By } from '@angular/platform-browser';
import { Product } from '../domain/product';
import { ProductRepository } from '../domain/product-repository';
import { ProductOffer } from '../domain/product-offer';
import * as _ from 'underscore';
import { ProductOfferRepository } from '../domain/product-offer-repository';
import createSpyObj = jasmine.createSpyObj;
import Spy = jasmine.Spy;

describe('The Product Offers page', () => {

  const productId: number = 1;
  const paramMap: ParamMap = convertToParamMap({ productId: productId });
  const product: Product = {
    id: 1,
    name: 'Product 1',
    manufacturer: 'Manufacturer 1'
  };
  const productOffers: ProductOffer[] = [
    { productId: productId, shopId: 1, shopName: 'Shop 1', price: 700 },
    { productId: productId, shopId: 2, shopName: 'Shop 2', price: 650 },
    { productId: productId, shopId: 3, shopName: 'Shop 3', price: 750 },
    { productId: productId, shopId: 4, shopName: 'Shop 4', price: 765 }
  ];

  let productRepository: ProductRepository;
  let productOfferRepository: ProductOfferRepository;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        ProductOffersPage
      ],
      providers: [
        { provide: PRODUCT_REPOSITORY, useFactory: () => createSpyObj('ProductRepository', ['findOneById']) },
        { provide: PRODUCT_OFFER_REPOSITORY, useFactory: () => createSpyObj('ProductOfferRepository', ['findAllByProductId']) },
        { provide: ActivatedRoute, useValue: { paramMap: Observable.of(paramMap) } }
      ]
    }).compileComponents();

    productRepository = TestBed.get(PRODUCT_REPOSITORY);
    productOfferRepository = TestBed.get(PRODUCT_OFFER_REPOSITORY);
    (<Spy> productRepository.findOneById).and.returnValue(Observable.of(product));
    (<Spy> productOfferRepository.findAllByProductId).and.returnValue(Observable.of(productOffers));
  }));

  it('should be created', async(() => {
    const fixture = TestBed.createComponent(ProductOffersPage);
    const productOffersPage = fixture.debugElement.componentInstance;
    expect(productOffersPage).toBeTruthy();
  }));

  it('should show the details of a product', (done)  => {
    const fixture = TestBed.createComponent(ProductOffersPage);
    fixture.detectChanges();
    fixture.whenStable()
      .then(() => {
        const productName = fixture.debugElement.query(By.css('#product-name'));
        const productManufacturer = fixture.debugElement.query(By.css('#product-manufacturer'));
        expect(productName.nativeElement.innerText).toBe(product.name);
        expect(productManufacturer.nativeElement.innerText).toBe(product.manufacturer);

        expect(productRepository.findOneById).toHaveBeenCalledWith(productId);
        done();
      });
  });

  it('should show the offers of the product', async(() => {
    const fixture = TestBed.createComponent(ProductOffersPage);
    fixture.detectChanges();
    fixture.whenStable()
      .then(() => {
        const offerElems = fixture.debugElement.queryAll(By.css('#offer-list .offer'));
        expect(offerElems.length).toBe(productOffers.length);
        _.forEach(offerElems, (offerElem, index) => {
          const expectedOffer = productOffers[index];
          expect(offerElem.query(By.css('.shop-name')).nativeElement.innerText).toBe(expectedOffer.shopName);
          expect(offerElem.query(By.css('.price')).nativeElement.innerText).toEqual(expectedOffer.price.toString());
        });

      });
  }));
});
