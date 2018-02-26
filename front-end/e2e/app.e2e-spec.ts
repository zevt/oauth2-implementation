import { Oauth2ImplementationPage } from './app.po';

describe('Oauth2 Implementation App', () => {
  let page: Oauth2ImplementationPage;

  beforeEach(() => {
    page = new Oauth2ImplementationPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!');
  });
});
