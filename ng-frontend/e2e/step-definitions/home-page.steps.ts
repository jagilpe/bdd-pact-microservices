import { defineSupportCode } from 'cucumber';
import { HomePO } from '../pages/home.po';

const chai = require('chai').use(require('chai-as-promised'));
const expect = chai.expect;

defineSupportCode(({ Given, Then }) => {

  Given('I am on the Home page', () => {
    return HomePO.navigateTo();
  });

  Then('I should get the welcome message {string}', (welcomeMessage) =>
    expect(HomePO.getWelcomeMessage()).to.eventually.equal(welcomeMessage)
  );

});
