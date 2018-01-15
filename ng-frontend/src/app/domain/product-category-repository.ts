import { Observable } from 'rxjs/Observable';
import { ProductCategory } from './product-category';

export interface ProductCategoryRepository {

  findAll(): Observable<ProductCategory[]>;

}
