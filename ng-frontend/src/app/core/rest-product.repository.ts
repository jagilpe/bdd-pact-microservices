import { ProductRepository } from '../domain/product-repository';
import { Product } from '../domain/product';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/empty';
import { HttpClient } from '@angular/common/http';
import { BACKEND_URL } from '../app.parameters';
import { Inject, Injectable } from '@angular/core';

@Injectable()
export class RestProductRepository implements ProductRepository {

  constructor(private http: HttpClient, @Inject(BACKEND_URL) private backendUrl: string) {}

  findAllByCategory(categoryId: number): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.backendUrl}/products?category=${categoryId}`);
  }

}
