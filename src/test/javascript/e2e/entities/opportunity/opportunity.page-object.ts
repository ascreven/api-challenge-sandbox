import { element, by, ElementFinder } from 'protractor';

export class OpportunityComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-opportunity div table .btn-danger'));
  title = element.all(by.css('jhi-opportunity div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getText();
  }
}

export class OpportunityUpdatePage {
  pageTitle = element(by.id('jhi-opportunity-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  titleInput = element(by.id('field_title'));
  solNumInput = element(by.id('field_solNum'));
  noticeidInput = element(by.id('field_noticeid'));
  stateInput = element(by.id('field_state'));
  zipInput = element(by.id('field_zip'));
  postedFromInput = element(by.id('field_postedFrom'));
  postedToInput = element(by.id('field_postedTo'));
  reponseDeadLineInput = element(by.id('field_reponseDeadLine'));
  classificationCodeInput = element(by.id('field_classificationCode'));
  naicsCodeInput = element(by.id('field_naicsCode'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setTitleInput(title: string): Promise<void> {
    await this.titleInput.sendKeys(title);
  }

  async getTitleInput(): Promise<string> {
    return await this.titleInput.getAttribute('value');
  }

  async setSolNumInput(solNum: string): Promise<void> {
    await this.solNumInput.sendKeys(solNum);
  }

  async getSolNumInput(): Promise<string> {
    return await this.solNumInput.getAttribute('value');
  }

  async setNoticeidInput(noticeid: string): Promise<void> {
    await this.noticeidInput.sendKeys(noticeid);
  }

  async getNoticeidInput(): Promise<string> {
    return await this.noticeidInput.getAttribute('value');
  }

  async setStateInput(state: string): Promise<void> {
    await this.stateInput.sendKeys(state);
  }

  async getStateInput(): Promise<string> {
    return await this.stateInput.getAttribute('value');
  }

  async setZipInput(zip: string): Promise<void> {
    await this.zipInput.sendKeys(zip);
  }

  async getZipInput(): Promise<string> {
    return await this.zipInput.getAttribute('value');
  }

  async setPostedFromInput(postedFrom: string): Promise<void> {
    await this.postedFromInput.sendKeys(postedFrom);
  }

  async getPostedFromInput(): Promise<string> {
    return await this.postedFromInput.getAttribute('value');
  }

  async setPostedToInput(postedTo: string): Promise<void> {
    await this.postedToInput.sendKeys(postedTo);
  }

  async getPostedToInput(): Promise<string> {
    return await this.postedToInput.getAttribute('value');
  }

  async setReponseDeadLineInput(reponseDeadLine: string): Promise<void> {
    await this.reponseDeadLineInput.sendKeys(reponseDeadLine);
  }

  async getReponseDeadLineInput(): Promise<string> {
    return await this.reponseDeadLineInput.getAttribute('value');
  }

  async setClassificationCodeInput(classificationCode: string): Promise<void> {
    await this.classificationCodeInput.sendKeys(classificationCode);
  }

  async getClassificationCodeInput(): Promise<string> {
    return await this.classificationCodeInput.getAttribute('value');
  }

  async setNaicsCodeInput(naicsCode: string): Promise<void> {
    await this.naicsCodeInput.sendKeys(naicsCode);
  }

  async getNaicsCodeInput(): Promise<string> {
    return await this.naicsCodeInput.getAttribute('value');
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class OpportunityDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-opportunity-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-opportunity'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
