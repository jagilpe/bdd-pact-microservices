import { Component, Inject, OnInit } from '@angular/core';
import { Product } from '../domain/product';
import { ActivatedRoute, Params } from '@angular/router';
import 'rxjs/add/operator/switchMap';
import 'rxjs/add/operator/map';
import { PRODUCT_REPOSITORY } from '../app-injectable-tokens';
import { ProductRepository } from '../domain/product-repository';

@Component({
  selector: 'category-page',
  templateUrl: './category.page.html',
  styleUrls: ['./category.page.scss']
})
export class CategoryPage implements OnInit {

  products: Array<Product>;

  constructor(@Inject(PRODUCT_REPOSITORY) private productRepository: ProductRepository,
              private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.paramMap
      .switchMap((params: Params) => this.productRepository.findAllByCategory(params.get('categoryId')))
      .subscribe(products => {
        this.products = products;
      });
  }

}
