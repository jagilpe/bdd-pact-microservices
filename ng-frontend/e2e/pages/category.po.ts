import { browser, by, element, promise } from 'protractor';

export class CategoryPo {

  constructor(private categoryId: number) {}

  navigateTo(): promise.Promise<void> {
    return browser.get(`/category/${this.categoryId}`);
  }

  getProductsCount(): promise.Promise<number> {
    return element.all(by.css('#product-list .product')).count();
  }

}
