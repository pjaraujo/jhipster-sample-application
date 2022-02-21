import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMoloniConfiguration, getMoloniConfigurationIdentifier } from '../moloni-configuration.model';

export type EntityResponseType = HttpResponse<IMoloniConfiguration>;
export type EntityArrayResponseType = HttpResponse<IMoloniConfiguration[]>;

@Injectable({ providedIn: 'root' })
export class MoloniConfigurationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/moloni-configurations');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(moloniConfiguration: IMoloniConfiguration): Observable<EntityResponseType> {
    return this.http.post<IMoloniConfiguration>(this.resourceUrl, moloniConfiguration, { observe: 'response' });
  }

  update(moloniConfiguration: IMoloniConfiguration): Observable<EntityResponseType> {
    return this.http.put<IMoloniConfiguration>(
      `${this.resourceUrl}/${getMoloniConfigurationIdentifier(moloniConfiguration) as number}`,
      moloniConfiguration,
      { observe: 'response' }
    );
  }

  partialUpdate(moloniConfiguration: IMoloniConfiguration): Observable<EntityResponseType> {
    return this.http.patch<IMoloniConfiguration>(
      `${this.resourceUrl}/${getMoloniConfigurationIdentifier(moloniConfiguration) as number}`,
      moloniConfiguration,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMoloniConfiguration>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMoloniConfiguration[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addMoloniConfigurationToCollectionIfMissing(
    moloniConfigurationCollection: IMoloniConfiguration[],
    ...moloniConfigurationsToCheck: (IMoloniConfiguration | null | undefined)[]
  ): IMoloniConfiguration[] {
    const moloniConfigurations: IMoloniConfiguration[] = moloniConfigurationsToCheck.filter(isPresent);
    if (moloniConfigurations.length > 0) {
      const moloniConfigurationCollectionIdentifiers = moloniConfigurationCollection.map(
        moloniConfigurationItem => getMoloniConfigurationIdentifier(moloniConfigurationItem)!
      );
      const moloniConfigurationsToAdd = moloniConfigurations.filter(moloniConfigurationItem => {
        const moloniConfigurationIdentifier = getMoloniConfigurationIdentifier(moloniConfigurationItem);
        if (moloniConfigurationIdentifier == null || moloniConfigurationCollectionIdentifiers.includes(moloniConfigurationIdentifier)) {
          return false;
        }
        moloniConfigurationCollectionIdentifiers.push(moloniConfigurationIdentifier);
        return true;
      });
      return [...moloniConfigurationsToAdd, ...moloniConfigurationCollection];
    }
    return moloniConfigurationCollection;
  }
}
