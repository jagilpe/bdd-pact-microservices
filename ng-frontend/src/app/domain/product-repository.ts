import { Product } from './product';
import { Observable } from 'rxjs/Observable';

export interface ProductRepository {

  findAllByCategory(categoryId: number): Observable<Product[]>;

  findOneById(productId: number): Observable<Product>;

}
