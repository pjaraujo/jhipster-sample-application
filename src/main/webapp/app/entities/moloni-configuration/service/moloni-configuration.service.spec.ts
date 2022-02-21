import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IMoloniConfiguration, MoloniConfiguration } from '../moloni-configuration.model';

import { MoloniConfigurationService } from './moloni-configuration.service';

describe('MoloniConfiguration Service', () => {
  let service: MoloniConfigurationService;
  let httpMock: HttpTestingController;
  let elemDefault: IMoloniConfiguration;
  let expectedResult: IMoloniConfiguration | IMoloniConfiguration[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MoloniConfigurationService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      username: 'AAAAAAA',
      password: 'AAAAAAA',
      company: 'AAAAAAA',
      client: 'AAAAAAA',
      secret: 'AAAAAAA',
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

    it('should create a MoloniConfiguration', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new MoloniConfiguration()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MoloniConfiguration', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          username: 'BBBBBB',
          password: 'BBBBBB',
          company: 'BBBBBB',
          client: 'BBBBBB',
          secret: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a MoloniConfiguration', () => {
      const patchObject = Object.assign(
        {
          username: 'BBBBBB',
          secret: 'BBBBBB',
        },
        new MoloniConfiguration()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MoloniConfiguration', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          username: 'BBBBBB',
          password: 'BBBBBB',
          company: 'BBBBBB',
          client: 'BBBBBB',
          secret: 'BBBBBB',
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

    it('should delete a MoloniConfiguration', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addMoloniConfigurationToCollectionIfMissing', () => {
      it('should add a MoloniConfiguration to an empty array', () => {
        const moloniConfiguration: IMoloniConfiguration = { id: 123 };
        expectedResult = service.addMoloniConfigurationToCollectionIfMissing([], moloniConfiguration);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(moloniConfiguration);
      });

      it('should not add a MoloniConfiguration to an array that contains it', () => {
        const moloniConfiguration: IMoloniConfiguration = { id: 123 };
        const moloniConfigurationCollection: IMoloniConfiguration[] = [
          {
            ...moloniConfiguration,
          },
          { id: 456 },
        ];
        expectedResult = service.addMoloniConfigurationToCollectionIfMissing(moloniConfigurationCollection, moloniConfiguration);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MoloniConfiguration to an array that doesn't contain it", () => {
        const moloniConfiguration: IMoloniConfiguration = { id: 123 };
        const moloniConfigurationCollection: IMoloniConfiguration[] = [{ id: 456 }];
        expectedResult = service.addMoloniConfigurationToCollectionIfMissing(moloniConfigurationCollection, moloniConfiguration);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(moloniConfiguration);
      });

      it('should add only unique MoloniConfiguration to an array', () => {
        const moloniConfigurationArray: IMoloniConfiguration[] = [{ id: 123 }, { id: 456 }, { id: 27181 }];
        const moloniConfigurationCollection: IMoloniConfiguration[] = [{ id: 123 }];
        expectedResult = service.addMoloniConfigurationToCollectionIfMissing(moloniConfigurationCollection, ...moloniConfigurationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const moloniConfiguration: IMoloniConfiguration = { id: 123 };
        const moloniConfiguration2: IMoloniConfiguration = { id: 456 };
        expectedResult = service.addMoloniConfigurationToCollectionIfMissing([], moloniConfiguration, moloniConfiguration2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(moloniConfiguration);
        expect(expectedResult).toContain(moloniConfiguration2);
      });

      it('should accept null and undefined values', () => {
        const moloniConfiguration: IMoloniConfiguration = { id: 123 };
        expectedResult = service.addMoloniConfigurationToCollectionIfMissing([], null, moloniConfiguration, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(moloniConfiguration);
      });

      it('should return initial array if no MoloniConfiguration is added', () => {
        const moloniConfigurationCollection: IMoloniConfiguration[] = [{ id: 123 }];
        expectedResult = service.addMoloniConfigurationToCollectionIfMissing(moloniConfigurationCollection, undefined, null);
        expect(expectedResult).toEqual(moloniConfigurationCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
