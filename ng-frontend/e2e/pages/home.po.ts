import { browser, by, element, promise } from 'protractor';

export class HomePO {

  static navigateTo(): promise.Promise<void> {
    return browser.get("/");
  }

  static getWelcomeMessage(): promise.Promise<string> {
    return element(by.css('h1')).getText();
  }
}
