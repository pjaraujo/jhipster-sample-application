import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IShippingProcess, getShippingProcessIdentifier } from '../shipping-process.model';

export type EntityResponseType = HttpResponse<IShippingProcess>;
export type EntityArrayResponseType = HttpResponse<IShippingProcess[]>;

@Injectable({ providedIn: 'root' })
export class ShippingProcessService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/shipping-processes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(shippingProcess: IShippingProcess): Observable<EntityResponseType> {
    return this.http.post<IShippingProcess>(this.resourceUrl, shippingProcess, { observe: 'response' });
  }

  update(shippingProcess: IShippingProcess): Observable<EntityResponseType> {
    return this.http.put<IShippingProcess>(
      `${this.resourceUrl}/${getShippingProcessIdentifier(shippingProcess) as number}`,
      shippingProcess,
      { observe: 'response' }
    );
  }

  partialUpdate(shippingProcess: IShippingProcess): Observable<EntityResponseType> {
    return this.http.patch<IShippingProcess>(
      `${this.resourceUrl}/${getShippingProcessIdentifier(shippingProcess) as number}`,
      shippingProcess,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IShippingProcess>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IShippingProcess[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addShippingProcessToCollectionIfMissing(
    shippingProcessCollection: IShippingProcess[],
    ...shippingProcessesToCheck: (IShippingProcess | null | undefined)[]
  ): IShippingProcess[] {
    const shippingProcesses: IShippingProcess[] = shippingProcessesToCheck.filter(isPresent);
    if (shippingProcesses.length > 0) {
      const shippingProcessCollectionIdentifiers = shippingProcessCollection.map(
        shippingProcessItem => getShippingProcessIdentifier(shippingProcessItem)!
      );
      const shippingProcessesToAdd = shippingProcesses.filter(shippingProcessItem => {
        const shippingProcessIdentifier = getShippingProcessIdentifier(shippingProcessItem);
        if (shippingProcessIdentifier == null || shippingProcessCollectionIdentifiers.includes(shippingProcessIdentifier)) {
          return false;
        }
        shippingProcessCollectionIdentifiers.push(shippingProcessIdentifier);
        return true;
      });
      return [...shippingProcessesToAdd, ...shippingProcessCollection];
    }
    return shippingProcessCollection;
  }
}
