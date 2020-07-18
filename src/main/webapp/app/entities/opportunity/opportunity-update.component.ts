import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IOpportunity, Opportunity } from 'app/shared/model/opportunity.model';
import { OpportunityService } from './opportunity.service';

@Component({
  selector: 'jhi-opportunity-update',
  templateUrl: './opportunity-update.component.html',
})
export class OpportunityUpdateComponent implements OnInit {
  isSaving = false;
  postedFromDp: any;
  postedToDp: any;

  editForm = this.fb.group({
    id: [],
    title: [],
    solNum: [],
    noticeid: [],
    state: [],
    zip: [],
    postedFrom: [],
    postedTo: [],
    reponseDeadLine: [],
    classificationCode: [],
    naicsCode: [],
  });

  constructor(protected opportunityService: OpportunityService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ opportunity }) => {
      this.updateForm(opportunity);
    });
  }

  updateForm(opportunity: IOpportunity): void {
    this.editForm.patchValue({
      id: opportunity.id,
      title: opportunity.title,
      solNum: opportunity.solNum,
      noticeid: opportunity.noticeid,
      state: opportunity.state,
      zip: opportunity.zip,
      postedFrom: opportunity.postedFrom,
      postedTo: opportunity.postedTo,
      reponseDeadLine: opportunity.reponseDeadLine,
      classificationCode: opportunity.classificationCode,
      naicsCode: opportunity.naicsCode,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const opportunity = this.createFromForm();
    if (opportunity.id !== undefined) {
      this.subscribeToSaveResponse(this.opportunityService.update(opportunity));
    } else {
      this.subscribeToSaveResponse(this.opportunityService.create(opportunity));
    }
  }

  private createFromForm(): IOpportunity {
    return {
      ...new Opportunity(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      solNum: this.editForm.get(['solNum'])!.value,
      noticeid: this.editForm.get(['noticeid'])!.value,
      state: this.editForm.get(['state'])!.value,
      zip: this.editForm.get(['zip'])!.value,
      postedFrom: this.editForm.get(['postedFrom'])!.value,
      postedTo: this.editForm.get(['postedTo'])!.value,
      reponseDeadLine: this.editForm.get(['reponseDeadLine'])!.value,
      classificationCode: this.editForm.get(['classificationCode'])!.value,
      naicsCode: this.editForm.get(['naicsCode'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOpportunity>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
