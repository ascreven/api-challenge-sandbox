import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { INaics, Naics } from 'app/shared/model/naics.model';
import { NaicsService } from './naics.service';

@Component({
  selector: 'jhi-naics-update',
  templateUrl: './naics-update.component.html',
})
export class NaicsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    description: [null, [Validators.maxLength(1024)]],
    code: [null, [Validators.maxLength(6)]],
    title: [],
  });

  constructor(protected naicsService: NaicsService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ naics }) => {
      this.updateForm(naics);
    });
  }

  updateForm(naics: INaics): void {
    this.editForm.patchValue({
      id: naics.id,
      description: naics.description,
      code: naics.code,
      title: naics.title,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const naics = this.createFromForm();
    if (naics.id !== undefined) {
      this.subscribeToSaveResponse(this.naicsService.update(naics));
    } else {
      this.subscribeToSaveResponse(this.naicsService.create(naics));
    }
  }

  private createFromForm(): INaics {
    return {
      ...new Naics(),
      id: this.editForm.get(['id'])!.value,
      description: this.editForm.get(['description'])!.value,
      code: this.editForm.get(['code'])!.value,
      title: this.editForm.get(['title'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INaics>>): void {
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
