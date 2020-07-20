import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { OpportunityComponentsPage, OpportunityDeleteDialog, OpportunityUpdatePage } from './opportunity.page-object';

const expect = chai.expect;

describe('Opportunity e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let opportunityComponentsPage: OpportunityComponentsPage;
  let opportunityUpdatePage: OpportunityUpdatePage;
  let opportunityDeleteDialog: OpportunityDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Opportunities', async () => {
    await navBarPage.goToEntity('opportunity');
    opportunityComponentsPage = new OpportunityComponentsPage();
    await browser.wait(ec.visibilityOf(opportunityComponentsPage.title), 5000);
    expect(await opportunityComponentsPage.getTitle()).to.eq('Opportunities');
    await browser.wait(
      ec.or(ec.visibilityOf(opportunityComponentsPage.entities), ec.visibilityOf(opportunityComponentsPage.noResult)),
      1000
    );
  });

  it('should load create Opportunity page', async () => {
    await opportunityComponentsPage.clickOnCreateButton();
    opportunityUpdatePage = new OpportunityUpdatePage();
    expect(await opportunityUpdatePage.getPageTitle()).to.eq('Create or edit a Opportunity');
    await opportunityUpdatePage.cancel();
  });

  it('should create and save Opportunities', async () => {
    const nbButtonsBeforeCreate = await opportunityComponentsPage.countDeleteButtons();

    await opportunityComponentsPage.clickOnCreateButton();

    await promise.all([
      opportunityUpdatePage.setTitleInput('title'),
      opportunityUpdatePage.setSolNumInput('solNum'),
      opportunityUpdatePage.setNoticeidInput('noticeid'),
      opportunityUpdatePage.setStateInput('state'),
      opportunityUpdatePage.setZipInput('zip'),
      opportunityUpdatePage.setPostedFromInput('2000-12-31'),
      opportunityUpdatePage.setPostedToInput('2000-12-31'),
      opportunityUpdatePage.setReponseDeadLineInput('reponseDeadLine'),
      opportunityUpdatePage.setClassificationCodeInput('classificationCode'),
      opportunityUpdatePage.naicsSelectLastOption(),
    ]);

    expect(await opportunityUpdatePage.getTitleInput()).to.eq('title', 'Expected Title value to be equals to title');
    expect(await opportunityUpdatePage.getSolNumInput()).to.eq('solNum', 'Expected SolNum value to be equals to solNum');
    expect(await opportunityUpdatePage.getNoticeidInput()).to.eq('noticeid', 'Expected Noticeid value to be equals to noticeid');
    expect(await opportunityUpdatePage.getStateInput()).to.eq('state', 'Expected State value to be equals to state');
    expect(await opportunityUpdatePage.getZipInput()).to.eq('zip', 'Expected Zip value to be equals to zip');
    expect(await opportunityUpdatePage.getPostedFromInput()).to.eq('2000-12-31', 'Expected postedFrom value to be equals to 2000-12-31');
    expect(await opportunityUpdatePage.getPostedToInput()).to.eq('2000-12-31', 'Expected postedTo value to be equals to 2000-12-31');
    expect(await opportunityUpdatePage.getReponseDeadLineInput()).to.eq(
      'reponseDeadLine',
      'Expected ReponseDeadLine value to be equals to reponseDeadLine'
    );
    expect(await opportunityUpdatePage.getClassificationCodeInput()).to.eq(
      'classificationCode',
      'Expected ClassificationCode value to be equals to classificationCode'
    );

    await opportunityUpdatePage.save();
    expect(await opportunityUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await opportunityComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Opportunity', async () => {
    const nbButtonsBeforeDelete = await opportunityComponentsPage.countDeleteButtons();
    await opportunityComponentsPage.clickOnLastDeleteButton();

    opportunityDeleteDialog = new OpportunityDeleteDialog();
    expect(await opportunityDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Opportunity?');
    await opportunityDeleteDialog.clickOnConfirmButton();

    expect(await opportunityComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
