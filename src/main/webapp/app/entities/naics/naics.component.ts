import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { INaics } from 'app/shared/model/naics.model';
import { NaicsService } from './naics.service';
import { NaicsDeleteDialogComponent } from './naics-delete-dialog.component';

@Component({
  selector: 'jhi-naics',
  templateUrl: './naics.component.html',
})
export class NaicsComponent implements OnInit, OnDestroy {
  naics?: INaics[];
  eventSubscriber?: Subscription;

  constructor(protected naicsService: NaicsService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.naicsService.query().subscribe((res: HttpResponse<INaics[]>) => (this.naics = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInNaics();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: INaics): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInNaics(): void {
    this.eventSubscriber = this.eventManager.subscribe('naicsListModification', () => this.loadAll());
  }

  delete(naics: INaics): void {
    const modalRef = this.modalService.open(NaicsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.naics = naics;
  }
}
