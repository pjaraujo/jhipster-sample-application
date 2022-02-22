import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMoloniExpeditionDocument, getMoloniExpeditionDocumentIdentifier } from '../moloni-expedition-document.model';

export type EntityResponseType = HttpResponse<IMoloniExpeditionDocument>;
export type EntityArrayResponseType = HttpResponse<IMoloniExpeditionDocument[]>;

@Injectable({ providedIn: 'root' })
export class MoloniExpeditionDocumentService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/moloni-expedition-documents');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(moloniExpeditionDocument: IMoloniExpeditionDocument): Observable<EntityResponseType> {
    return this.http.post<IMoloniExpeditionDocument>(this.resourceUrl, moloniExpeditionDocument, { observe: 'response' });
  }

  update(moloniExpeditionDocument: IMoloniExpeditionDocument): Observable<EntityResponseType> {
    return this.http.put<IMoloniExpeditionDocument>(
      `${this.resourceUrl}/${getMoloniExpeditionDocumentIdentifier(moloniExpeditionDocument) as number}`,
      moloniExpeditionDocument,
      { observe: 'response' }
    );
  }

  partialUpdate(moloniExpeditionDocument: IMoloniExpeditionDocument): Observable<EntityResponseType> {
    return this.http.patch<IMoloniExpeditionDocument>(
      `${this.resourceUrl}/${getMoloniExpeditionDocumentIdentifier(moloniExpeditionDocument) as number}`,
      moloniExpeditionDocument,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMoloniExpeditionDocument>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMoloniExpeditionDocument[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addMoloniExpeditionDocumentToCollectionIfMissing(
    moloniExpeditionDocumentCollection: IMoloniExpeditionDocument[],
    ...moloniExpeditionDocumentsToCheck: (IMoloniExpeditionDocument | null | undefined)[]
  ): IMoloniExpeditionDocument[] {
    const moloniExpeditionDocuments: IMoloniExpeditionDocument[] = moloniExpeditionDocumentsToCheck.filter(isPresent);
    if (moloniExpeditionDocuments.length > 0) {
      const moloniExpeditionDocumentCollectionIdentifiers = moloniExpeditionDocumentCollection.map(
        moloniExpeditionDocumentItem => getMoloniExpeditionDocumentIdentifier(moloniExpeditionDocumentItem)!
      );
      const moloniExpeditionDocumentsToAdd = moloniExpeditionDocuments.filter(moloniExpeditionDocumentItem => {
        const moloniExpeditionDocumentIdentifier = getMoloniExpeditionDocumentIdentifier(moloniExpeditionDocumentItem);
        if (
          moloniExpeditionDocumentIdentifier == null ||
          moloniExpeditionDocumentCollectionIdentifiers.includes(moloniExpeditionDocumentIdentifier)
        ) {
          return false;
        }
        moloniExpeditionDocumentCollectionIdentifiers.push(moloniExpeditionDocumentIdentifier);
        return true;
      });
      return [...moloniExpeditionDocumentsToAdd, ...moloniExpeditionDocumentCollection];
    }
    return moloniExpeditionDocumentCollection;
  }
}
