import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INaics } from 'app/shared/model/naics.model';

@Component({
  selector: 'jhi-naics-detail',
  templateUrl: './naics-detail.component.html',
})
export class NaicsDetailComponent implements OnInit {
  naics: INaics | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ naics }) => (this.naics = naics));
  }

  previousState(): void {
    window.history.back();
  }
}
