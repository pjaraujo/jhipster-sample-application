import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMoloniDocumentType, getMoloniDocumentTypeIdentifier } from '../moloni-document-type.model';

export type EntityResponseType = HttpResponse<IMoloniDocumentType>;
export type EntityArrayResponseType = HttpResponse<IMoloniDocumentType[]>;

@Injectable({ providedIn: 'root' })
export class MoloniDocumentTypeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/moloni-document-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(moloniDocumentType: IMoloniDocumentType): Observable<EntityResponseType> {
    return this.http.post<IMoloniDocumentType>(this.resourceUrl, moloniDocumentType, { observe: 'response' });
  }

  update(moloniDocumentType: IMoloniDocumentType): Observable<EntityResponseType> {
    return this.http.put<IMoloniDocumentType>(
      `${this.resourceUrl}/${getMoloniDocumentTypeIdentifier(moloniDocumentType) as number}`,
      moloniDocumentType,
      { observe: 'response' }
    );
  }

  partialUpdate(moloniDocumentType: IMoloniDocumentType): Observable<EntityResponseType> {
    return this.http.patch<IMoloniDocumentType>(
      `${this.resourceUrl}/${getMoloniDocumentTypeIdentifier(moloniDocumentType) as number}`,
      moloniDocumentType,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMoloniDocumentType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMoloniDocumentType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addMoloniDocumentTypeToCollectionIfMissing(
    moloniDocumentTypeCollection: IMoloniDocumentType[],
    ...moloniDocumentTypesToCheck: (IMoloniDocumentType | null | undefined)[]
  ): IMoloniDocumentType[] {
    const moloniDocumentTypes: IMoloniDocumentType[] = moloniDocumentTypesToCheck.filter(isPresent);
    if (moloniDocumentTypes.length > 0) {
      const moloniDocumentTypeCollectionIdentifiers = moloniDocumentTypeCollection.map(
        moloniDocumentTypeItem => getMoloniDocumentTypeIdentifier(moloniDocumentTypeItem)!
      );
      const moloniDocumentTypesToAdd = moloniDocumentTypes.filter(moloniDocumentTypeItem => {
        const moloniDocumentTypeIdentifier = getMoloniDocumentTypeIdentifier(moloniDocumentTypeItem);
        if (moloniDocumentTypeIdentifier == null || moloniDocumentTypeCollectionIdentifiers.includes(moloniDocumentTypeIdentifier)) {
          return false;
        }
        moloniDocumentTypeCollectionIdentifiers.push(moloniDocumentTypeIdentifier);
        return true;
      });
      return [...moloniDocumentTypesToAdd, ...moloniDocumentTypeCollection];
    }
    return moloniDocumentTypeCollection;
  }
}
