import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MoloniDocumentTypeService } from '../service/moloni-document-type.service';
import { IMoloniDocumentType, MoloniDocumentType } from '../moloni-document-type.model';
import { IMoloniConfiguration } from 'app/entities/moloni-configuration/moloni-configuration.model';
import { MoloniConfigurationService } from 'app/entities/moloni-configuration/service/moloni-configuration.service';

import { MoloniDocumentTypeUpdateComponent } from './moloni-document-type-update.component';

describe('MoloniDocumentType Management Update Component', () => {
  let comp: MoloniDocumentTypeUpdateComponent;
  let fixture: ComponentFixture<MoloniDocumentTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let moloniDocumentTypeService: MoloniDocumentTypeService;
  let moloniConfigurationService: MoloniConfigurationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MoloniDocumentTypeUpdateComponent],
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
      .overrideTemplate(MoloniDocumentTypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MoloniDocumentTypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    moloniDocumentTypeService = TestBed.inject(MoloniDocumentTypeService);
    moloniConfigurationService = TestBed.inject(MoloniConfigurationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call MoloniConfiguration query and add missing value', () => {
      const moloniDocumentType: IMoloniDocumentType = { id: 456 };
      const config1: IMoloniConfiguration = { id: 68589 };
      moloniDocumentType.config1 = config1;
      const config2: IMoloniConfiguration = { id: 41332 };
      moloniDocumentType.config2 = config2;
      const config3: IMoloniConfiguration = { id: 36863 };
      moloniDocumentType.config3 = config3;
      const config4: IMoloniConfiguration = { id: 13164 };
      moloniDocumentType.config4 = config4;

      const moloniConfigurationCollection: IMoloniConfiguration[] = [{ id: 68792 }];
      jest.spyOn(moloniConfigurationService, 'query').mockReturnValue(of(new HttpResponse({ body: moloniConfigurationCollection })));
      const additionalMoloniConfigurations = [config1, config2, config3, config4];
      const expectedCollection: IMoloniConfiguration[] = [...additionalMoloniConfigurations, ...moloniConfigurationCollection];
      jest.spyOn(moloniConfigurationService, 'addMoloniConfigurationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ moloniDocumentType });
      comp.ngOnInit();

      expect(moloniConfigurationService.query).toHaveBeenCalled();
      expect(moloniConfigurationService.addMoloniConfigurationToCollectionIfMissing).toHaveBeenCalledWith(
        moloniConfigurationCollection,
        ...additionalMoloniConfigurations
      );
      expect(comp.moloniConfigurationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const moloniDocumentType: IMoloniDocumentType = { id: 456 };
      const config1: IMoloniConfiguration = { id: 62835 };
      moloniDocumentType.config1 = config1;
      const config2: IMoloniConfiguration = { id: 708 };
      moloniDocumentType.config2 = config2;
      const config3: IMoloniConfiguration = { id: 67033 };
      moloniDocumentType.config3 = config3;
      const config4: IMoloniConfiguration = { id: 32921 };
      moloniDocumentType.config4 = config4;

      activatedRoute.data = of({ moloniDocumentType });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(moloniDocumentType));
      expect(comp.moloniConfigurationsSharedCollection).toContain(config1);
      expect(comp.moloniConfigurationsSharedCollection).toContain(config2);
      expect(comp.moloniConfigurationsSharedCollection).toContain(config3);
      expect(comp.moloniConfigurationsSharedCollection).toContain(config4);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MoloniDocumentType>>();
      const moloniDocumentType = { id: 123 };
      jest.spyOn(moloniDocumentTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ moloniDocumentType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: moloniDocumentType }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(moloniDocumentTypeService.update).toHaveBeenCalledWith(moloniDocumentType);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MoloniDocumentType>>();
      const moloniDocumentType = new MoloniDocumentType();
      jest.spyOn(moloniDocumentTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ moloniDocumentType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: moloniDocumentType }));
      saveSubject.complete();

      // THEN
      expect(moloniDocumentTypeService.create).toHaveBeenCalledWith(moloniDocumentType);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MoloniDocumentType>>();
      const moloniDocumentType = { id: 123 };
      jest.spyOn(moloniDocumentTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ moloniDocumentType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(moloniDocumentTypeService.update).toHaveBeenCalledWith(moloniDocumentType);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackMoloniConfigurationById', () => {
      it('Should return tracked MoloniConfiguration primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackMoloniConfigurationById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
