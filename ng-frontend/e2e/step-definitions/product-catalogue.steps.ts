import { defineSupportCode } from 'cucumber';
import { apiBackend } from '../pact/init-pact.step';

const pact = require('pact');
const { eachLike } = pact.Matchers;

defineSupportCode(({ Given }) => {

  const categoryIds = {
    "Smartphones": { id: 1, name: "Smartphones" }
  };

  const product = {
    id: 1,
    name: 'iPhone 8',
    manufacturer: 'Apple'
  };

  Given('there are {int} products in the {string} category', (itemCount: number, category: string) => {
    const categoryId = categoryIds[category].id;
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

  Given('there are {int} product categories', (categoriesCount) => {
    const category = categoryIds["Smartphones"];
    return apiBackend.addInteraction({
      state: `there are ${categoriesCount} categories`,
      uponReceiving: `A request for a list of categories`,
      withRequest: {
        method: 'GET',
        path: '/api/v1/categories'
      },
      willRespondWith: {
        status: 200,
        headers: { 'Content-Type': 'application/json' },
        body: eachLike(category, { min: categoriesCount })
      }
    });
  });

});
