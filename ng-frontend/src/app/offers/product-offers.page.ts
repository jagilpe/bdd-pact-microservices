import { Component, Inject, OnInit } from '@angular/core';
import { Product } from '../domain/product';
import { ActivatedRoute, Params } from '@angular/router';
import { PRODUCT_REPOSITORY } from '../app-injectable-tokens';
import { ProductRepository } from '../domain/product-repository';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/switchMap';

@Component({
  selector: 'product-offers-page',
  templateUrl: './product-offers.page.html',
  styleUrls: [ './product-offers.page.scss' ]
})
export class ProductOffersPage implements OnInit {
  product: Product;

  constructor(private activatedRoute: ActivatedRoute,
              @Inject(PRODUCT_REPOSITORY) private productRepository: ProductRepository) {}

  public ngOnInit():void  {
    this.activatedRoute.paramMap
      .map((params: Params) => params.get('productId'))
      .switchMap(productId => this.productRepository.findOneById(productId))
      .subscribe(product => this.product = product);
  }

}
