import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMoloniReceptionDocument, MoloniReceptionDocument } from '../moloni-reception-document.model';
import { MoloniReceptionDocumentService } from '../service/moloni-reception-document.service';

@Injectable({ providedIn: 'root' })
export class MoloniReceptionDocumentRoutingResolveService implements Resolve<IMoloniReceptionDocument> {
  constructor(protected service: MoloniReceptionDocumentService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMoloniReceptionDocument> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((moloniReceptionDocument: HttpResponse<MoloniReceptionDocument>) => {
          if (moloniReceptionDocument.body) {
            return of(moloniReceptionDocument.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MoloniReceptionDocument());
  }
}
