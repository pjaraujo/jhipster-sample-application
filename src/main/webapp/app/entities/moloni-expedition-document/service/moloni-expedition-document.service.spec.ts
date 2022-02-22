import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { MoloniEndpoints } from 'app/entities/enumerations/moloni-endpoints.model';
import { IMoloniExpeditionDocument, MoloniExpeditionDocument } from '../moloni-expedition-document.model';

import { MoloniExpeditionDocumentService } from './moloni-expedition-document.service';

describe('MoloniExpeditionDocument Service', () => {
  let service: MoloniExpeditionDocumentService;
  let httpMock: HttpTestingController;
  let elemDefault: IMoloniExpeditionDocument;
  let expectedResult: IMoloniExpeditionDocument | IMoloniExpeditionDocument[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MoloniExpeditionDocumentService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      origin: MoloniEndpoints.INVOICES,
      destination: MoloniEndpoints.INVOICES,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a MoloniExpeditionDocument', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new MoloniExpeditionDocument()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MoloniExpeditionDocument', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          origin: 'BBBBBB',
          destination: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a MoloniExpeditionDocument', () => {
      const patchObject = Object.assign(
        {
          origin: 'BBBBBB',
          destination: 'BBBBBB',
        },
        new MoloniExpeditionDocument()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MoloniExpeditionDocument', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          origin: 'BBBBBB',
          destination: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a MoloniExpeditionDocument', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addMoloniExpeditionDocumentToCollectionIfMissing', () => {
      it('should add a MoloniExpeditionDocument to an empty array', () => {
        const moloniExpeditionDocument: IMoloniExpeditionDocument = { id: 123 };
        expectedResult = service.addMoloniExpeditionDocumentToCollectionIfMissing([], moloniExpeditionDocument);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(moloniExpeditionDocument);
      });

      it('should not add a MoloniExpeditionDocument to an array that contains it', () => {
        const moloniExpeditionDocument: IMoloniExpeditionDocument = { id: 123 };
        const moloniExpeditionDocumentCollection: IMoloniExpeditionDocument[] = [
          {
            ...moloniExpeditionDocument,
          },
          { id: 456 },
        ];
        expectedResult = service.addMoloniExpeditionDocumentToCollectionIfMissing(
          moloniExpeditionDocumentCollection,
          moloniExpeditionDocument
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MoloniExpeditionDocument to an array that doesn't contain it", () => {
        const moloniExpeditionDocument: IMoloniExpeditionDocument = { id: 123 };
        const moloniExpeditionDocumentCollection: IMoloniExpeditionDocument[] = [{ id: 456 }];
        expectedResult = service.addMoloniExpeditionDocumentToCollectionIfMissing(
          moloniExpeditionDocumentCollection,
          moloniExpeditionDocument
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(moloniExpeditionDocument);
      });

      it('should add only unique MoloniExpeditionDocument to an array', () => {
        const moloniExpeditionDocumentArray: IMoloniExpeditionDocument[] = [{ id: 123 }, { id: 456 }, { id: 80740 }];
        const moloniExpeditionDocumentCollection: IMoloniExpeditionDocument[] = [{ id: 123 }];
        expectedResult = service.addMoloniExpeditionDocumentToCollectionIfMissing(
          moloniExpeditionDocumentCollection,
          ...moloniExpeditionDocumentArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const moloniExpeditionDocument: IMoloniExpeditionDocument = { id: 123 };
        const moloniExpeditionDocument2: IMoloniExpeditionDocument = { id: 456 };
        expectedResult = service.addMoloniExpeditionDocumentToCollectionIfMissing([], moloniExpeditionDocument, moloniExpeditionDocument2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(moloniExpeditionDocument);
        expect(expectedResult).toContain(moloniExpeditionDocument2);
      });

      it('should accept null and undefined values', () => {
        const moloniExpeditionDocument: IMoloniExpeditionDocument = { id: 123 };
        expectedResult = service.addMoloniExpeditionDocumentToCollectionIfMissing([], null, moloniExpeditionDocument, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(moloniExpeditionDocument);
      });

      it('should return initial array if no MoloniExpeditionDocument is added', () => {
        const moloniExpeditionDocumentCollection: IMoloniExpeditionDocument[] = [{ id: 123 }];
        expectedResult = service.addMoloniExpeditionDocumentToCollectionIfMissing(moloniExpeditionDocumentCollection, undefined, null);
        expect(expectedResult).toEqual(moloniExpeditionDocumentCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
