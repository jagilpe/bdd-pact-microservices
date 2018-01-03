import { ProductOffer } from './product-offer';
import { Observable } from 'rxjs/Observable';

export interface ProductOfferRepository {

  findAllByProductId(productId: number): Observable<ProductOffer[]>;

}
