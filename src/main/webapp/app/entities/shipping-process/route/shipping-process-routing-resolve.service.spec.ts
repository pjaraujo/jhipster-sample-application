import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IShippingProcess, ShippingProcess } from '../shipping-process.model';
import { ShippingProcessService } from '../service/shipping-process.service';

import { ShippingProcessRoutingResolveService } from './shipping-process-routing-resolve.service';

describe('ShippingProcess routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ShippingProcessRoutingResolveService;
  let service: ShippingProcessService;
  let resultShippingProcess: IShippingProcess | undefined;

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
    routingResolveService = TestBed.inject(ShippingProcessRoutingResolveService);
    service = TestBed.inject(ShippingProcessService);
    resultShippingProcess = undefined;
  });

  describe('resolve', () => {
    it('should return IShippingProcess returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultShippingProcess = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultShippingProcess).toEqual({ id: 123 });
    });

    it('should return new IShippingProcess if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultShippingProcess = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultShippingProcess).toEqual(new ShippingProcess());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as ShippingProcess })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultShippingProcess = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultShippingProcess).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
