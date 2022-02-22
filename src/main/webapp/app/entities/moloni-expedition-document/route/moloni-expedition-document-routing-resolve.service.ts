import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMoloniExpeditionDocument, MoloniExpeditionDocument } from '../moloni-expedition-document.model';
import { MoloniExpeditionDocumentService } from '../service/moloni-expedition-document.service';

@Injectable({ providedIn: 'root' })
export class MoloniExpeditionDocumentRoutingResolveService implements Resolve<IMoloniExpeditionDocument> {
  constructor(protected service: MoloniExpeditionDocumentService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMoloniExpeditionDocument> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((moloniExpeditionDocument: HttpResponse<MoloniExpeditionDocument>) => {
          if (moloniExpeditionDocument.body) {
            return of(moloniExpeditionDocument.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MoloniExpeditionDocument());
  }
}
