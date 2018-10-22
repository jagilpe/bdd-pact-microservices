import { Given, Then } from 'cucumber';
import { HomePO } from '../pages/home.po';

const chai = require('chai').use(require('chai-as-promised'));
const expect = chai.expect;

Given('I am on the Home page', () => {
  return HomePO.navigateTo();
});

Then('I should get the welcome message {string}', (welcomeMessage) =>
  expect(HomePO.getWelcomeMessage()).to.eventually.equal(welcomeMessage)
);

Then('I should see {int} items in the category list', (categoriesCount) =>
  expect(HomePO.getCategoriesCount()).to.eventually.equal(categoriesCount));
