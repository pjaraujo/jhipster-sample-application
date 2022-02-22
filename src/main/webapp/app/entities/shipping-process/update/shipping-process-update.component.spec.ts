import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ShippingProcessService } from '../service/shipping-process.service';
import { IShippingProcess, ShippingProcess } from '../shipping-process.model';

import { ShippingProcessUpdateComponent } from './shipping-process-update.component';

describe('ShippingProcess Management Update Component', () => {
  let comp: ShippingProcessUpdateComponent;
  let fixture: ComponentFixture<ShippingProcessUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let shippingProcessService: ShippingProcessService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ShippingProcessUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ShippingProcessUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ShippingProcessUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    shippingProcessService = TestBed.inject(ShippingProcessService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const shippingProcess: IShippingProcess = { id: 456 };

      activatedRoute.data = of({ shippingProcess });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(shippingProcess));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ShippingProcess>>();
      const shippingProcess = { id: 123 };
      jest.spyOn(shippingProcessService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ shippingProcess });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: shippingProcess }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(shippingProcessService.update).toHaveBeenCalledWith(shippingProcess);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ShippingProcess>>();
      const shippingProcess = new ShippingProcess();
      jest.spyOn(shippingProcessService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ shippingProcess });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: shippingProcess }));
      saveSubject.complete();

      // THEN
      expect(shippingProcessService.create).toHaveBeenCalledWith(shippingProcess);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ShippingProcess>>();
      const shippingProcess = { id: 123 };
      jest.spyOn(shippingProcessService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ shippingProcess });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(shippingProcessService.update).toHaveBeenCalledWith(shippingProcess);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
