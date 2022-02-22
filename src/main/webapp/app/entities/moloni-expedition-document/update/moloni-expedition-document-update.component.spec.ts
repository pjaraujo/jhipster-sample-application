import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MoloniExpeditionDocumentService } from '../service/moloni-expedition-document.service';
import { IMoloniExpeditionDocument, MoloniExpeditionDocument } from '../moloni-expedition-document.model';
import { IShippingProcess } from 'app/entities/shipping-process/shipping-process.model';
import { ShippingProcessService } from 'app/entities/shipping-process/service/shipping-process.service';
import { IMoloniConfiguration } from 'app/entities/moloni-configuration/moloni-configuration.model';
import { MoloniConfigurationService } from 'app/entities/moloni-configuration/service/moloni-configuration.service';

import { MoloniExpeditionDocumentUpdateComponent } from './moloni-expedition-document-update.component';

describe('MoloniExpeditionDocument Management Update Component', () => {
  let comp: MoloniExpeditionDocumentUpdateComponent;
  let fixture: ComponentFixture<MoloniExpeditionDocumentUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let moloniExpeditionDocumentService: MoloniExpeditionDocumentService;
  let shippingProcessService: ShippingProcessService;
  let moloniConfigurationService: MoloniConfigurationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MoloniExpeditionDocumentUpdateComponent],
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
      .overrideTemplate(MoloniExpeditionDocumentUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MoloniExpeditionDocumentUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    moloniExpeditionDocumentService = TestBed.inject(MoloniExpeditionDocumentService);
    shippingProcessService = TestBed.inject(ShippingProcessService);
    moloniConfigurationService = TestBed.inject(MoloniConfigurationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ShippingProcess query and add missing value', () => {
      const moloniExpeditionDocument: IMoloniExpeditionDocument = { id: 456 };
      const shippingProcess: IShippingProcess = { id: 4525 };
      moloniExpeditionDocument.shippingProcess = shippingProcess;

      const shippingProcessCollection: IShippingProcess[] = [{ id: 89263 }];
      jest.spyOn(shippingProcessService, 'query').mockReturnValue(of(new HttpResponse({ body: shippingProcessCollection })));
      const additionalShippingProcesses = [shippingProcess];
      const expectedCollection: IShippingProcess[] = [...additionalShippingProcesses, ...shippingProcessCollection];
      jest.spyOn(shippingProcessService, 'addShippingProcessToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ moloniExpeditionDocument });
      comp.ngOnInit();

      expect(shippingProcessService.query).toHaveBeenCalled();
      expect(shippingProcessService.addShippingProcessToCollectionIfMissing).toHaveBeenCalledWith(
        shippingProcessCollection,
        ...additionalShippingProcesses
      );
      expect(comp.shippingProcessesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call MoloniConfiguration query and add missing value', () => {
      const moloniExpeditionDocument: IMoloniExpeditionDocument = { id: 456 };
      const config: IMoloniConfiguration = { id: 29947 };
      moloniExpeditionDocument.config = config;

      const moloniConfigurationCollection: IMoloniConfiguration[] = [{ id: 42569 }];
      jest.spyOn(moloniConfigurationService, 'query').mockReturnValue(of(new HttpResponse({ body: moloniConfigurationCollection })));
      const additionalMoloniConfigurations = [config];
      const expectedCollection: IMoloniConfiguration[] = [...additionalMoloniConfigurations, ...moloniConfigurationCollection];
      jest.spyOn(moloniConfigurationService, 'addMoloniConfigurationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ moloniExpeditionDocument });
      comp.ngOnInit();

      expect(moloniConfigurationService.query).toHaveBeenCalled();
      expect(moloniConfigurationService.addMoloniConfigurationToCollectionIfMissing).toHaveBeenCalledWith(
        moloniConfigurationCollection,
        ...additionalMoloniConfigurations
      );
      expect(comp.moloniConfigurationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const moloniExpeditionDocument: IMoloniExpeditionDocument = { id: 456 };
      const shippingProcess: IShippingProcess = { id: 96409 };
      moloniExpeditionDocument.shippingProcess = shippingProcess;
      const config: IMoloniConfiguration = { id: 42171 };
      moloniExpeditionDocument.config = config;

      activatedRoute.data = of({ moloniExpeditionDocument });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(moloniExpeditionDocument));
      expect(comp.shippingProcessesSharedCollection).toContain(shippingProcess);
      expect(comp.moloniConfigurationsSharedCollection).toContain(config);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MoloniExpeditionDocument>>();
      const moloniExpeditionDocument = { id: 123 };
      jest.spyOn(moloniExpeditionDocumentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ moloniExpeditionDocument });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: moloniExpeditionDocument }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(moloniExpeditionDocumentService.update).toHaveBeenCalledWith(moloniExpeditionDocument);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MoloniExpeditionDocument>>();
      const moloniExpeditionDocument = new MoloniExpeditionDocument();
      jest.spyOn(moloniExpeditionDocumentService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ moloniExpeditionDocument });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: moloniExpeditionDocument }));
      saveSubject.complete();

      // THEN
      expect(moloniExpeditionDocumentService.create).toHaveBeenCalledWith(moloniExpeditionDocument);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MoloniExpeditionDocument>>();
      const moloniExpeditionDocument = { id: 123 };
      jest.spyOn(moloniExpeditionDocumentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ moloniExpeditionDocument });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(moloniExpeditionDocumentService.update).toHaveBeenCalledWith(moloniExpeditionDocument);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackShippingProcessById', () => {
      it('Should return tracked ShippingProcess primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackShippingProcessById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackMoloniConfigurationById', () => {
      it('Should return tracked MoloniConfiguration primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackMoloniConfigurationById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
