import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DocumentType } from 'app/entities/enumerations/document-type.model';
import { IMoloniDocumentType, MoloniDocumentType } from '../moloni-document-type.model';

import { MoloniDocumentTypeService } from './moloni-document-type.service';

describe('MoloniDocumentType Service', () => {
  let service: MoloniDocumentTypeService;
  let httpMock: HttpTestingController;
  let elemDefault: IMoloniDocumentType;
  let expectedResult: IMoloniDocumentType | IMoloniDocumentType[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MoloniDocumentTypeService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      documentType: DocumentType.INVOICES,
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

    it('should create a MoloniDocumentType', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new MoloniDocumentType()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MoloniDocumentType', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
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

    it('should partial update a MoloniDocumentType', () => {
      const patchObject = Object.assign({}, new MoloniDocumentType());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MoloniDocumentType', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
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

    it('should delete a MoloniDocumentType', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addMoloniDocumentTypeToCollectionIfMissing', () => {
      it('should add a MoloniDocumentType to an empty array', () => {
        const moloniDocumentType: IMoloniDocumentType = { id: 123 };
        expectedResult = service.addMoloniDocumentTypeToCollectionIfMissing([], moloniDocumentType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(moloniDocumentType);
      });

      it('should not add a MoloniDocumentType to an array that contains it', () => {
        const moloniDocumentType: IMoloniDocumentType = { id: 123 };
        const moloniDocumentTypeCollection: IMoloniDocumentType[] = [
          {
            ...moloniDocumentType,
          },
          { id: 456 },
        ];
        expectedResult = service.addMoloniDocumentTypeToCollectionIfMissing(moloniDocumentTypeCollection, moloniDocumentType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MoloniDocumentType to an array that doesn't contain it", () => {
        const moloniDocumentType: IMoloniDocumentType = { id: 123 };
        const moloniDocumentTypeCollection: IMoloniDocumentType[] = [{ id: 456 }];
        expectedResult = service.addMoloniDocumentTypeToCollectionIfMissing(moloniDocumentTypeCollection, moloniDocumentType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(moloniDocumentType);
      });

      it('should add only unique MoloniDocumentType to an array', () => {
        const moloniDocumentTypeArray: IMoloniDocumentType[] = [{ id: 123 }, { id: 456 }, { id: 627 }];
        const moloniDocumentTypeCollection: IMoloniDocumentType[] = [{ id: 123 }];
        expectedResult = service.addMoloniDocumentTypeToCollectionIfMissing(moloniDocumentTypeCollection, ...moloniDocumentTypeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const moloniDocumentType: IMoloniDocumentType = { id: 123 };
        const moloniDocumentType2: IMoloniDocumentType = { id: 456 };
        expectedResult = service.addMoloniDocumentTypeToCollectionIfMissing([], moloniDocumentType, moloniDocumentType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(moloniDocumentType);
        expect(expectedResult).toContain(moloniDocumentType2);
      });

      it('should accept null and undefined values', () => {
        const moloniDocumentType: IMoloniDocumentType = { id: 123 };
        expectedResult = service.addMoloniDocumentTypeToCollectionIfMissing([], null, moloniDocumentType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(moloniDocumentType);
      });

      it('should return initial array if no MoloniDocumentType is added', () => {
        const moloniDocumentTypeCollection: IMoloniDocumentType[] = [{ id: 123 }];
        expectedResult = service.addMoloniDocumentTypeToCollectionIfMissing(moloniDocumentTypeCollection, undefined, null);
        expect(expectedResult).toEqual(moloniDocumentTypeCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
