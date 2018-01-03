///<reference path="../../../node_modules/rxjs/add/operator/map.d.ts"/>
import { Component, Inject, OnInit } from '@angular/core';
import { Product } from '../domain/product';
import { ActivatedRoute, Params } from '@angular/router';
import { PRODUCT_OFFER_REPOSITORY, PRODUCT_REPOSITORY } from '../app-injectable-tokens';
import { ProductRepository } from '../domain/product-repository';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/switchMap';
import { ProductOfferRepository } from '../domain/product-offer-repository';
import { Observable } from 'rxjs/Observable';
import { ProductOffer } from '../domain/product-offer';

@Component({
  selector: 'product-offers-page',
  templateUrl: './product-offers.page.html',
  styleUrls: [ './product-offers.page.scss' ]
})
export class ProductOffersPage implements OnInit {
  product: Product;
  productOffers: ProductOffer[];

  constructor(private activatedRoute: ActivatedRoute,
              @Inject(PRODUCT_REPOSITORY) private productRepository: ProductRepository,
              @Inject(PRODUCT_OFFER_REPOSITORY) private productOfferRepository: ProductOfferRepository) {}

  public ngOnInit():void  {
    let productId: Observable<number> = this.activatedRoute.paramMap
      .map((params: Params) => params.get('productId'));

    productId
      .switchMap(productId => this.productRepository.findOneById(productId))
      .subscribe(product => this.product = product);

    productId
      .switchMap(productId => this.productOfferRepository.findAllByProductId(productId))
      .subscribe(offers => this.productOffers = offers);
  }

}
