import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IShippingProcess, ShippingProcess } from '../shipping-process.model';
import { ShippingProcessService } from '../service/shipping-process.service';

@Injectable({ providedIn: 'root' })
export class ShippingProcessRoutingResolveService implements Resolve<IShippingProcess> {
  constructor(protected service: ShippingProcessService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IShippingProcess> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((shippingProcess: HttpResponse<ShippingProcess>) => {
          if (shippingProcess.body) {
            return of(shippingProcess.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ShippingProcess());
  }
}
