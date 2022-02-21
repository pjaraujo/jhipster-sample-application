import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MoloniConfigurationService } from '../service/moloni-configuration.service';
import { IMoloniConfiguration, MoloniConfiguration } from '../moloni-configuration.model';

import { MoloniConfigurationUpdateComponent } from './moloni-configuration-update.component';

describe('MoloniConfiguration Management Update Component', () => {
  let comp: MoloniConfigurationUpdateComponent;
  let fixture: ComponentFixture<MoloniConfigurationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let moloniConfigurationService: MoloniConfigurationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MoloniConfigurationUpdateComponent],
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
      .overrideTemplate(MoloniConfigurationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MoloniConfigurationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    moloniConfigurationService = TestBed.inject(MoloniConfigurationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const moloniConfiguration: IMoloniConfiguration = { id: 456 };

      activatedRoute.data = of({ moloniConfiguration });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(moloniConfiguration));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MoloniConfiguration>>();
      const moloniConfiguration = { id: 123 };
      jest.spyOn(moloniConfigurationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ moloniConfiguration });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: moloniConfiguration }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(moloniConfigurationService.update).toHaveBeenCalledWith(moloniConfiguration);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MoloniConfiguration>>();
      const moloniConfiguration = new MoloniConfiguration();
      jest.spyOn(moloniConfigurationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ moloniConfiguration });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: moloniConfiguration }));
      saveSubject.complete();

      // THEN
      expect(moloniConfigurationService.create).toHaveBeenCalledWith(moloniConfiguration);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MoloniConfiguration>>();
      const moloniConfiguration = { id: 123 };
      jest.spyOn(moloniConfigurationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ moloniConfiguration });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(moloniConfigurationService.update).toHaveBeenCalledWith(moloniConfiguration);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
