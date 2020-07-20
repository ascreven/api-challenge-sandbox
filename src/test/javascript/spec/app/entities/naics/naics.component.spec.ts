import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SandboxTestModule } from '../../../test.module';
import { NaicsComponent } from 'app/entities/naics/naics.component';
import { NaicsService } from 'app/entities/naics/naics.service';
import { Naics } from 'app/shared/model/naics.model';

describe('Component Tests', () => {
  describe('Naics Management Component', () => {
    let comp: NaicsComponent;
    let fixture: ComponentFixture<NaicsComponent>;
    let service: NaicsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SandboxTestModule],
        declarations: [NaicsComponent],
      })
        .overrideTemplate(NaicsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NaicsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NaicsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Naics(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.naics && comp.naics[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
