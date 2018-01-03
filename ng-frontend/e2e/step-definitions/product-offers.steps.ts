import { defineSupportCode } from 'cucumber';
import { Product } from '../../src/app/domain/product';
import { ProductOffersPo } from '../pages/product-offers.po';
import { apiBackend } from '../pact/init-pact.step';
import { ProductOffer } from '../../src/app/domain/product-offer';

const chai = require('chai').use(require('chai-as-promised'));
const expect = chai.expect;

const pact = require('pact');
const { somethingLike, eachLike } = pact.Matchers;

defineSupportCode(({ Given, When, Then, After }) => {

  const products: { [s: string]: Product } = {
    'iPhone 8': {
      id: 1,
      name: 'iPhone 8',
      manufacturer: 'Apple'
    }
  };

  const offers: { [s: string]: ProductOffer } = {
    'iPhone 8': { productId: 1, shopId: 1, shopName: 'Shop 1', price: 700.00 }
  };

  let productOffersPage: ProductOffersPo;

  Given('there is a product {string}', (productName: string) => {
    const productId = products[productName].id;
    const url = `/api/v1/products/${productId}`;

    return apiBackend.addInteraction({
      state: `there is a product ${productName}`,
      uponReceiving: 'a request for the details of a product',
      withRequest: {
        method: 'GET',
        path: url
      },
      willRespondWith: {
        status: 200,
        headers: { 'Content-Type': 'application/json' },
        body: somethingLike(products[productName])
      }
    });
  });

  Given('the {string} product has {int} offers', (productName: string, offersCount: number) => {
    const productId = products[productName].id;
    const url = `/api/v1/offers/product/${productId}`;

    return apiBackend.addInteraction({
      state: `there are ${offersCount} offers for product ${productId}`,
      uponReceiving: 'a request for the offers of a product',
      withRequest: {
        method: 'GET',
        path: url
      },
      willRespondWith: {
        status: 200,
        headers: { 'Content-Type': 'application/json' },
        body: eachLike(offers[productName], { min: offersCount })
      }
    });
  });

  When('I go to the {string} product offers page', (productName: string) => {
    const productId = products[productName].id;
    productOffersPage = new ProductOffersPo(productId);
    return productOffersPage.navigateTo();
  });

  Then('I get the {string} product details', (productName: string) => {
    return expect(productOffersPage.getProductDetails()).to.eventually.deep.equal(products[productName]);
  });

  Then('I get {int} items in the list of offers', (offersCount: number) => {
    return expect(productOffersPage.getOffersCount()).to.eventually.equal(offersCount);
  });

  After(() => apiBackend.verify());

});
