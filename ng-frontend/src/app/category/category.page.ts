import { Component, Inject, OnInit } from '@angular/core';
import { Product } from '../domain/product';
import { HttpClient } from '@angular/common/http';
import { BACKEND_URL } from '../app.parameters';
import { ActivatedRoute, Params } from '@angular/router';
import 'rxjs/add/operator/switchMap';
import 'rxjs/add/operator/map';

@Component({
  selector: 'category-page',
  templateUrl: './category.page.html',
  styleUrls: ['./category.page.scss']
})
export class CategoryPage implements OnInit {

  products: Array<Product>;

  constructor(private http: HttpClient,
              @Inject(BACKEND_URL) private backendUrl: string,
              private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.paramMap
      .map((params: Params) => `${this.backendUrl}/products?category=${params.get('categoryId')}`)
      .switchMap(url => this.http.get<Product[]>(url))
      .subscribe(products => {
        this.products = products;
      });
  }

}
