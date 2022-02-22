import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { MoloniEndpoints } from 'app/entities/enumerations/moloni-endpoints.model';
import { DocumentType } from 'app/entities/enumerations/document-type.model';
import { IMoloniReceptionDocument, MoloniReceptionDocument } from '../moloni-reception-document.model';

import { MoloniReceptionDocumentService } from './moloni-reception-document.service';

describe('MoloniReceptionDocument Service', () => {
  let service: MoloniReceptionDocumentService;
  let httpMock: HttpTestingController;
  let elemDefault: IMoloniReceptionDocument;
  let expectedResult: IMoloniReceptionDocument | IMoloniReceptionDocument[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MoloniReceptionDocumentService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      origin: MoloniEndpoints.INVOICES,
      destination: MoloniEndpoints.INVOICES,
      documentType: DocumentType.WAREHOUSE_TRANSFER,
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

    it('should create a MoloniReceptionDocument', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new MoloniReceptionDocument()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MoloniReceptionDocument', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          origin: 'BBBBBB',
          destination: 'BBBBBB',
          documentType: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a MoloniReceptionDocument', () => {
      const patchObject = Object.assign({}, new MoloniReceptionDocument());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MoloniReceptionDocument', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          origin: 'BBBBBB',
          destination: 'BBBBBB',
          documentType: 'BBBBBB',
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

    it('should delete a MoloniReceptionDocument', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addMoloniReceptionDocumentToCollectionIfMissing', () => {
      it('should add a MoloniReceptionDocument to an empty array', () => {
        const moloniReceptionDocument: IMoloniReceptionDocument = { id: 123 };
        expectedResult = service.addMoloniReceptionDocumentToCollectionIfMissing([], moloniReceptionDocument);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(moloniReceptionDocument);
      });

      it('should not add a MoloniReceptionDocument to an array that contains it', () => {
        const moloniReceptionDocument: IMoloniReceptionDocument = { id: 123 };
        const moloniReceptionDocumentCollection: IMoloniReceptionDocument[] = [
          {
            ...moloniReceptionDocument,
          },
          { id: 456 },
        ];
        expectedResult = service.addMoloniReceptionDocumentToCollectionIfMissing(
          moloniReceptionDocumentCollection,
          moloniReceptionDocument
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MoloniReceptionDocument to an array that doesn't contain it", () => {
        const moloniReceptionDocument: IMoloniReceptionDocument = { id: 123 };
        const moloniReceptionDocumentCollection: IMoloniReceptionDocument[] = [{ id: 456 }];
        expectedResult = service.addMoloniReceptionDocumentToCollectionIfMissing(
          moloniReceptionDocumentCollection,
          moloniReceptionDocument
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(moloniReceptionDocument);
      });

      it('should add only unique MoloniReceptionDocument to an array', () => {
        const moloniReceptionDocumentArray: IMoloniReceptionDocument[] = [{ id: 123 }, { id: 456 }, { id: 33009 }];
        const moloniReceptionDocumentCollection: IMoloniReceptionDocument[] = [{ id: 123 }];
        expectedResult = service.addMoloniReceptionDocumentToCollectionIfMissing(
          moloniReceptionDocumentCollection,
          ...moloniReceptionDocumentArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const moloniReceptionDocument: IMoloniReceptionDocument = { id: 123 };
        const moloniReceptionDocument2: IMoloniReceptionDocument = { id: 456 };
        expectedResult = service.addMoloniReceptionDocumentToCollectionIfMissing([], moloniReceptionDocument, moloniReceptionDocument2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(moloniReceptionDocument);
        expect(expectedResult).toContain(moloniReceptionDocument2);
      });

      it('should accept null and undefined values', () => {
        const moloniReceptionDocument: IMoloniReceptionDocument = { id: 123 };
        expectedResult = service.addMoloniReceptionDocumentToCollectionIfMissing([], null, moloniReceptionDocument, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(moloniReceptionDocument);
      });

      it('should return initial array if no MoloniReceptionDocument is added', () => {
        const moloniReceptionDocumentCollection: IMoloniReceptionDocument[] = [{ id: 123 }];
        expectedResult = service.addMoloniReceptionDocumentToCollectionIfMissing(moloniReceptionDocumentCollection, undefined, null);
        expect(expectedResult).toEqual(moloniReceptionDocumentCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
