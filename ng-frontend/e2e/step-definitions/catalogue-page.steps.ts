import { When, Then } from 'cucumber';
import { CategoryPo } from '../pages/category.po';

const chai = require('chai').use(require('chai-as-promised'));
const expect = chai.expect;

let categoryPage: CategoryPo;

const categoryIds = {
  "Smartphones": 1
};

When('I go to the {string} category page', (category: string) => {
  categoryPage = new CategoryPo(categoryIds[category]);
  return categoryPage.navigateTo();
});

Then('I should get {int} items in the products list', (count: number) =>
  expect(categoryPage.getProductsCount()).to.eventually.equal(count));
