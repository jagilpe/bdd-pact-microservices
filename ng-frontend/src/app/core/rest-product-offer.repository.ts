import { ProductOfferRepository } from '../domain/product-offer-repository';
import { Observable } from 'rxjs/Observable';
import { ProductOffer } from '../domain/product-offer';
import { Inject, Injectable } from '@angular/core';
import { BACKEND_URL } from '../app.parameters';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class RestProductOfferRepository implements ProductOfferRepository {

  constructor(private http: HttpClient, @Inject(BACKEND_URL) private backendUrl: string) {}

  findAllByProductId(productId: number): Observable<ProductOffer[]> {
    return this.http.get<ProductOffer[]>(`${this.backendUrl}/offers/product/${productId}`);
  }

}
