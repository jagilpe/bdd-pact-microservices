import { Component, Inject, OnInit } from '@angular/core';
import { ProductCategory } from '../domain/product-category';
import { PRODUCT_CATEGORY_REPOSITORY } from '../app-injectable-tokens';
import { ProductCategoryRepository } from '../domain/product-category-repository';

@Component({
  selector: 'home-page',
  templateUrl: './home.page.html',
  styleUrls: ['./home.page.scss']
})
export class HomePage implements OnInit {
  title: string = 'My Product Catalogue';
  categories: Array<ProductCategory>;

  constructor(@Inject(PRODUCT_CATEGORY_REPOSITORY) private productCategoryRepository: ProductCategoryRepository) {}

  ngOnInit(): void {
    this.productCategoryRepository.findAll()
      .subscribe(categories => this.categories = categories);
  }

}
