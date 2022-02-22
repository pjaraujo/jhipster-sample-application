import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MoloniReceptionDocumentService } from '../service/moloni-reception-document.service';
import { IMoloniReceptionDocument, MoloniReceptionDocument } from '../moloni-reception-document.model';
import { IMoloniConfiguration } from 'app/entities/moloni-configuration/moloni-configuration.model';
import { MoloniConfigurationService } from 'app/entities/moloni-configuration/service/moloni-configuration.service';

import { MoloniReceptionDocumentUpdateComponent } from './moloni-reception-document-update.component';

describe('MoloniReceptionDocument Management Update Component', () => {
  let comp: MoloniReceptionDocumentUpdateComponent;
  let fixture: ComponentFixture<MoloniReceptionDocumentUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let moloniReceptionDocumentService: MoloniReceptionDocumentService;
  let moloniConfigurationService: MoloniConfigurationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MoloniReceptionDocumentUpdateComponent],
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
      .overrideTemplate(MoloniReceptionDocumentUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MoloniReceptionDocumentUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    moloniReceptionDocumentService = TestBed.inject(MoloniReceptionDocumentService);
    moloniConfigurationService = TestBed.inject(MoloniConfigurationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call MoloniConfiguration query and add missing value', () => {
      const moloniReceptionDocument: IMoloniReceptionDocument = { id: 456 };
      const config: IMoloniConfiguration = { id: 25033 };
      moloniReceptionDocument.config = config;

      const moloniConfigurationCollection: IMoloniConfiguration[] = [{ id: 75262 }];
      jest.spyOn(moloniConfigurationService, 'query').mockReturnValue(of(new HttpResponse({ body: moloniConfigurationCollection })));
      const additionalMoloniConfigurations = [config];
      const expectedCollection: IMoloniConfiguration[] = [...additionalMoloniConfigurations, ...moloniConfigurationCollection];
      jest.spyOn(moloniConfigurationService, 'addMoloniConfigurationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ moloniReceptionDocument });
      comp.ngOnInit();

      expect(moloniConfigurationService.query).toHaveBeenCalled();
      expect(moloniConfigurationService.addMoloniConfigurationToCollectionIfMissing).toHaveBeenCalledWith(
        moloniConfigurationCollection,
        ...additionalMoloniConfigurations
      );
      expect(comp.moloniConfigurationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const moloniReceptionDocument: IMoloniReceptionDocument = { id: 456 };
      const config: IMoloniConfiguration = { id: 65152 };
      moloniReceptionDocument.config = config;

      activatedRoute.data = of({ moloniReceptionDocument });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(moloniReceptionDocument));
      expect(comp.moloniConfigurationsSharedCollection).toContain(config);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MoloniReceptionDocument>>();
      const moloniReceptionDocument = { id: 123 };
      jest.spyOn(moloniReceptionDocumentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ moloniReceptionDocument });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: moloniReceptionDocument }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(moloniReceptionDocumentService.update).toHaveBeenCalledWith(moloniReceptionDocument);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MoloniReceptionDocument>>();
      const moloniReceptionDocument = new MoloniReceptionDocument();
      jest.spyOn(moloniReceptionDocumentService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ moloniReceptionDocument });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: moloniReceptionDocument }));
      saveSubject.complete();

      // THEN
      expect(moloniReceptionDocumentService.create).toHaveBeenCalledWith(moloniReceptionDocument);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MoloniReceptionDocument>>();
      const moloniReceptionDocument = { id: 123 };
      jest.spyOn(moloniReceptionDocumentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ moloniReceptionDocument });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(moloniReceptionDocumentService.update).toHaveBeenCalledWith(moloniReceptionDocument);
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
