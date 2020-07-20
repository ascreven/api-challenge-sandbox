import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SandboxTestModule } from '../../../test.module';
import { NaicsUpdateComponent } from 'app/entities/naics/naics-update.component';
import { NaicsService } from 'app/entities/naics/naics.service';
import { Naics } from 'app/shared/model/naics.model';

describe('Component Tests', () => {
  describe('Naics Management Update Component', () => {
    let comp: NaicsUpdateComponent;
    let fixture: ComponentFixture<NaicsUpdateComponent>;
    let service: NaicsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SandboxTestModule],
        declarations: [NaicsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(NaicsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NaicsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NaicsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Naics(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Naics();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
