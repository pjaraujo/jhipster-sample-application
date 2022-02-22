import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IShippingProcess, ShippingProcess } from '../shipping-process.model';

import { ShippingProcessService } from './shipping-process.service';

describe('ShippingProcess Service', () => {
  let service: ShippingProcessService;
  let httpMock: HttpTestingController;
  let elemDefault: IShippingProcess;
  let expectedResult: IShippingProcess | IShippingProcess[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ShippingProcessService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
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

    it('should create a ShippingProcess', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new ShippingProcess()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ShippingProcess', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ShippingProcess', () => {
      const patchObject = Object.assign({}, new ShippingProcess());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ShippingProcess', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
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

    it('should delete a ShippingProcess', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addShippingProcessToCollectionIfMissing', () => {
      it('should add a ShippingProcess to an empty array', () => {
        const shippingProcess: IShippingProcess = { id: 123 };
        expectedResult = service.addShippingProcessToCollectionIfMissing([], shippingProcess);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(shippingProcess);
      });

      it('should not add a ShippingProcess to an array that contains it', () => {
        const shippingProcess: IShippingProcess = { id: 123 };
        const shippingProcessCollection: IShippingProcess[] = [
          {
            ...shippingProcess,
          },
          { id: 456 },
        ];
        expectedResult = service.addShippingProcessToCollectionIfMissing(shippingProcessCollection, shippingProcess);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ShippingProcess to an array that doesn't contain it", () => {
        const shippingProcess: IShippingProcess = { id: 123 };
        const shippingProcessCollection: IShippingProcess[] = [{ id: 456 }];
        expectedResult = service.addShippingProcessToCollectionIfMissing(shippingProcessCollection, shippingProcess);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(shippingProcess);
      });

      it('should add only unique ShippingProcess to an array', () => {
        const shippingProcessArray: IShippingProcess[] = [{ id: 123 }, { id: 456 }, { id: 12363 }];
        const shippingProcessCollection: IShippingProcess[] = [{ id: 123 }];
        expectedResult = service.addShippingProcessToCollectionIfMissing(shippingProcessCollection, ...shippingProcessArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const shippingProcess: IShippingProcess = { id: 123 };
        const shippingProcess2: IShippingProcess = { id: 456 };
        expectedResult = service.addShippingProcessToCollectionIfMissing([], shippingProcess, shippingProcess2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(shippingProcess);
        expect(expectedResult).toContain(shippingProcess2);
      });

      it('should accept null and undefined values', () => {
        const shippingProcess: IShippingProcess = { id: 123 };
        expectedResult = service.addShippingProcessToCollectionIfMissing([], null, shippingProcess, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(shippingProcess);
      });

      it('should return initial array if no ShippingProcess is added', () => {
        const shippingProcessCollection: IShippingProcess[] = [{ id: 123 }];
        expectedResult = service.addShippingProcessToCollectionIfMissing(shippingProcessCollection, undefined, null);
        expect(expectedResult).toEqual(shippingProcessCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
