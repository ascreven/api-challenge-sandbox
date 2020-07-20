import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { NaicsComponentsPage, NaicsDeleteDialog, NaicsUpdatePage } from './naics.page-object';

const expect = chai.expect;

describe('Naics e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let naicsComponentsPage: NaicsComponentsPage;
  let naicsUpdatePage: NaicsUpdatePage;
  let naicsDeleteDialog: NaicsDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Naics', async () => {
    await navBarPage.goToEntity('naics');
    naicsComponentsPage = new NaicsComponentsPage();
    await browser.wait(ec.visibilityOf(naicsComponentsPage.title), 5000);
    expect(await naicsComponentsPage.getTitle()).to.eq('Naics');
    await browser.wait(ec.or(ec.visibilityOf(naicsComponentsPage.entities), ec.visibilityOf(naicsComponentsPage.noResult)), 1000);
  });

  it('should load create Naics page', async () => {
    await naicsComponentsPage.clickOnCreateButton();
    naicsUpdatePage = new NaicsUpdatePage();
    expect(await naicsUpdatePage.getPageTitle()).to.eq('Create or edit a Naics');
    await naicsUpdatePage.cancel();
  });

  it('should create and save Naics', async () => {
    const nbButtonsBeforeCreate = await naicsComponentsPage.countDeleteButtons();

    await naicsComponentsPage.clickOnCreateButton();

    await promise.all([
      naicsUpdatePage.setDescriptionInput('description'),
      naicsUpdatePage.setCodeInput('code'),
      naicsUpdatePage.setTitleInput('title'),
    ]);

    expect(await naicsUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
    expect(await naicsUpdatePage.getCodeInput()).to.eq('code', 'Expected Code value to be equals to code');
    expect(await naicsUpdatePage.getTitleInput()).to.eq('title', 'Expected Title value to be equals to title');

    await naicsUpdatePage.save();
    expect(await naicsUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await naicsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Naics', async () => {
    const nbButtonsBeforeDelete = await naicsComponentsPage.countDeleteButtons();
    await naicsComponentsPage.clickOnLastDeleteButton();

    naicsDeleteDialog = new NaicsDeleteDialog();
    expect(await naicsDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Naics?');
    await naicsDeleteDialog.clickOnConfirmButton();

    expect(await naicsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
