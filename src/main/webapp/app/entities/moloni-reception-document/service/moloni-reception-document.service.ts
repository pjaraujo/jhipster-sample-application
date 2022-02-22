import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMoloniReceptionDocument, getMoloniReceptionDocumentIdentifier } from '../moloni-reception-document.model';

export type EntityResponseType = HttpResponse<IMoloniReceptionDocument>;
export type EntityArrayResponseType = HttpResponse<IMoloniReceptionDocument[]>;

@Injectable({ providedIn: 'root' })
export class MoloniReceptionDocumentService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/moloni-reception-documents');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(moloniReceptionDocument: IMoloniReceptionDocument): Observable<EntityResponseType> {
    return this.http.post<IMoloniReceptionDocument>(this.resourceUrl, moloniReceptionDocument, { observe: 'response' });
  }

  update(moloniReceptionDocument: IMoloniReceptionDocument): Observable<EntityResponseType> {
    return this.http.put<IMoloniReceptionDocument>(
      `${this.resourceUrl}/${getMoloniReceptionDocumentIdentifier(moloniReceptionDocument) as number}`,
      moloniReceptionDocument,
      { observe: 'response' }
    );
  }

  partialUpdate(moloniReceptionDocument: IMoloniReceptionDocument): Observable<EntityResponseType> {
    return this.http.patch<IMoloniReceptionDocument>(
      `${this.resourceUrl}/${getMoloniReceptionDocumentIdentifier(moloniReceptionDocument) as number}`,
      moloniReceptionDocument,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMoloniReceptionDocument>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMoloniReceptionDocument[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addMoloniReceptionDocumentToCollectionIfMissing(
    moloniReceptionDocumentCollection: IMoloniReceptionDocument[],
    ...moloniReceptionDocumentsToCheck: (IMoloniReceptionDocument | null | undefined)[]
  ): IMoloniReceptionDocument[] {
    const moloniReceptionDocuments: IMoloniReceptionDocument[] = moloniReceptionDocumentsToCheck.filter(isPresent);
    if (moloniReceptionDocuments.length > 0) {
      const moloniReceptionDocumentCollectionIdentifiers = moloniReceptionDocumentCollection.map(
        moloniReceptionDocumentItem => getMoloniReceptionDocumentIdentifier(moloniReceptionDocumentItem)!
      );
      const moloniReceptionDocumentsToAdd = moloniReceptionDocuments.filter(moloniReceptionDocumentItem => {
        const moloniReceptionDocumentIdentifier = getMoloniReceptionDocumentIdentifier(moloniReceptionDocumentItem);
        if (
          moloniReceptionDocumentIdentifier == null ||
          moloniReceptionDocumentCollectionIdentifiers.includes(moloniReceptionDocumentIdentifier)
        ) {
          return false;
        }
        moloniReceptionDocumentCollectionIdentifiers.push(moloniReceptionDocumentIdentifier);
        return true;
      });
      return [...moloniReceptionDocumentsToAdd, ...moloniReceptionDocumentCollection];
    }
    return moloniReceptionDocumentCollection;
  }
}
