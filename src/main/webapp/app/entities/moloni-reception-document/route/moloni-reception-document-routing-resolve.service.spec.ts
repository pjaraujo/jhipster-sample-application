import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IMoloniReceptionDocument, MoloniReceptionDocument } from '../moloni-reception-document.model';
import { MoloniReceptionDocumentService } from '../service/moloni-reception-document.service';

import { MoloniReceptionDocumentRoutingResolveService } from './moloni-reception-document-routing-resolve.service';

describe('MoloniReceptionDocument routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: MoloniReceptionDocumentRoutingResolveService;
  let service: MoloniReceptionDocumentService;
  let resultMoloniReceptionDocument: IMoloniReceptionDocument | undefined;

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
    routingResolveService = TestBed.inject(MoloniReceptionDocumentRoutingResolveService);
    service = TestBed.inject(MoloniReceptionDocumentService);
    resultMoloniReceptionDocument = undefined;
  });

  describe('resolve', () => {
    it('should return IMoloniReceptionDocument returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultMoloniReceptionDocument = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultMoloniReceptionDocument).toEqual({ id: 123 });
    });

    it('should return new IMoloniReceptionDocument if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultMoloniReceptionDocument = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultMoloniReceptionDocument).toEqual(new MoloniReceptionDocument());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as MoloniReceptionDocument })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultMoloniReceptionDocument = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultMoloniReceptionDocument).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
