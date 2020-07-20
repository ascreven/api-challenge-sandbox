import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SandboxTestModule } from '../../../test.module';
import { NaicsDetailComponent } from 'app/entities/naics/naics-detail.component';
import { Naics } from 'app/shared/model/naics.model';

describe('Component Tests', () => {
  describe('Naics Management Detail Component', () => {
    let comp: NaicsDetailComponent;
    let fixture: ComponentFixture<NaicsDetailComponent>;
    const route = ({ data: of({ naics: new Naics(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SandboxTestModule],
        declarations: [NaicsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(NaicsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NaicsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load naics on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.naics).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
