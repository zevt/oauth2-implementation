import { browser, by, element } from 'protractor';

export class Oauth2ImplementationPage {
  navigateTo() {
    return browser.get('/');
  }

  getParagraphText() {
    return element(by.css('app-root h1')).getText();
  }
}
