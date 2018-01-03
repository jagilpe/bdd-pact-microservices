import { browser, by, element, promise } from 'protractor';
import { Product } from '../../src/app/domain/product';

export class ProductOffersPo {

  constructor(private productId: number) {}

  public navigateTo(): promise.Promise<void> {
    return browser.get(`/product-offers/${this.productId}`);
  }

  public getProductDetails(): promise.Promise<Product> {
    let productNamePromise = element(by.css('#product-name')).getText();
    let manufacturerPromise = element(by.css('#product-manufacturer')).getText();
    return promise.all([productNamePromise, manufacturerPromise])
      .then(elements => {
        return {
          id: this.productId,
          name: elements[0],
          manufacturer: elements[1]
        };
      });
  }

  public getOffersCount(): promise.Promise<number> {
    return element.all(by.css('#offer-list .offer')).count();
  }
}
