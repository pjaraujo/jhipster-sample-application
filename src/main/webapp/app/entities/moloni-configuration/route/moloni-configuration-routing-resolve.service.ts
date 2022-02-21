import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMoloniConfiguration, MoloniConfiguration } from '../moloni-configuration.model';
import { MoloniConfigurationService } from '../service/moloni-configuration.service';

@Injectable({ providedIn: 'root' })
export class MoloniConfigurationRoutingResolveService implements Resolve<IMoloniConfiguration> {
  constructor(protected service: MoloniConfigurationService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMoloniConfiguration> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((moloniConfiguration: HttpResponse<MoloniConfiguration>) => {
          if (moloniConfiguration.body) {
            return of(moloniConfiguration.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MoloniConfiguration());
  }
}
