import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMoloniDocumentType, MoloniDocumentType } from '../moloni-document-type.model';
import { MoloniDocumentTypeService } from '../service/moloni-document-type.service';

@Injectable({ providedIn: 'root' })
export class MoloniDocumentTypeRoutingResolveService implements Resolve<IMoloniDocumentType> {
  constructor(protected service: MoloniDocumentTypeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMoloniDocumentType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((moloniDocumentType: HttpResponse<MoloniDocumentType>) => {
          if (moloniDocumentType.body) {
            return of(moloniDocumentType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MoloniDocumentType());
  }
}
