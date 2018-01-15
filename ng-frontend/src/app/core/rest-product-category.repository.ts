import { Inject, Injectable } from '@angular/core';
import { ProductCategoryRepository } from '../domain/product-category-repository';
import { Observable } from 'rxjs/Observable';
import { ProductCategory } from '../domain/product-category';
import { HttpClient } from '@angular/common/http';
import { BACKEND_URL } from '../app.parameters';

@Injectable()
export class RestProductCategoryRepository implements ProductCategoryRepository {

  constructor(private http: HttpClient, @Inject(BACKEND_URL) private backendUrl: string) {}

  findAll(): Observable<ProductCategory[]> {
    return this.http.get<ProductCategory[]>(`${this.backendUrl}/categories`);
  }

}
