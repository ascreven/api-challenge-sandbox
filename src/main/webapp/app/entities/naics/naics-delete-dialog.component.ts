import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INaics } from 'app/shared/model/naics.model';
import { NaicsService } from './naics.service';

@Component({
  templateUrl: './naics-delete-dialog.component.html',
})
export class NaicsDeleteDialogComponent {
  naics?: INaics;

  constructor(protected naicsService: NaicsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.naicsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('naicsListModification');
      this.activeModal.close();
    });
  }
}
