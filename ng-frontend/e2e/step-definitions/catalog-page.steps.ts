import { defineSupportCode } from 'cucumber';
import { CategoryPo } from '../pages/category.po';
import { apiBackend } from '../pact/init-pact.step';

const pact = require('pact');
const { eachLike } = pact.Matchers;

const chai = require('chai').use(require('chai-as-promised'));
const expect = chai.expect;

defineSupportCode(({ Given, When, Then }) => {
  let categoryPage: CategoryPo;

  const categoryIds = {
    "Smartphones": 1
  };

  const product = {
    id: 1,
    name: 'iPhone 8',
    manufacturer: 'Apple'
  };

  Given('there are {int} products in the {string} category', (itemCount: number, category: string) => {
    const categoryId = categoryIds[category];
    return apiBackend.addInteraction({

      state: `there are ${itemCount} products in the category ${categoryId}`,
      uponReceiving: `A request for products in the category ${categoryId}`,
      withRequest: {
        method: 'GET',
        path: '/api/v1/products',
        query: { 'category': `${categoryId}`}
      },
      willRespondWith: {
        status: 200,
        headers: { 'Content-Type': 'application/json' },
        body: eachLike(product, { min: itemCount })
      }
    });
  });

  When('I go to the {string} category page', (category: string) => {
    categoryPage = new CategoryPo(categoryIds[category]);
    return categoryPage.navigateTo();
  });

  Then('I should get {int} items in the products list', (count: number) => {
    return expect(categoryPage.getProductsCount()).to.eventually.equal(count)
      .then(() => apiBackend.verify());
  });

});
