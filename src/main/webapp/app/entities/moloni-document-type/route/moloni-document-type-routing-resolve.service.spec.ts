import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IMoloniDocumentType, MoloniDocumentType } from '../moloni-document-type.model';
import { MoloniDocumentTypeService } from '../service/moloni-document-type.service';

import { MoloniDocumentTypeRoutingResolveService } from './moloni-document-type-routing-resolve.service';

describe('MoloniDocumentType routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: MoloniDocumentTypeRoutingResolveService;
  let service: MoloniDocumentTypeService;
  let resultMoloniDocumentType: IMoloniDocumentType | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(MoloniDocumentTypeRoutingResolveService);
    service = TestBed.inject(MoloniDocumentTypeService);
    resultMoloniDocumentType = undefined;
  });

  describe('resolve', () => {
    it('should return IMoloniDocumentType returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultMoloniDocumentType = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultMoloniDocumentType).toEqual({ id: 123 });
    });

    it('should return new IMoloniDocumentType if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultMoloniDocumentType = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultMoloniDocumentType).toEqual(new MoloniDocumentType());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as MoloniDocumentType })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultMoloniDocumentType = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultMoloniDocumentType).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
