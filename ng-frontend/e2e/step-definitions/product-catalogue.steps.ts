import { defineSupportCode } from 'cucumber';
import { apiBackend } from '../pact/init-pact.step';

const pact = require('pact');
const { eachLike } = pact.Matchers;

defineSupportCode(({ Given }) => {

  const categoryIds = {
    "Smartphones": { id: 1, name: "Smartphones" }
  };

  Given('there are {int} product categories', (categoriesCount) => {
    const category = categoryIds["Smartphones"];
    return apiBackend.addInteraction({
      state: `there are ${categoriesCount} categories`,
      uponReceiving: `A request a list of categories`,
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
