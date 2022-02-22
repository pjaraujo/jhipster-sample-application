import { IShippingProcess } from 'app/entities/shipping-process/shipping-process.model';
import { IMoloniConfiguration } from 'app/entities/moloni-configuration/moloni-configuration.model';
import { MoloniEndpoints } from 'app/entities/enumerations/moloni-endpoints.model';

export interface IMoloniExpeditionDocument {
  id?: number;
  origin?: MoloniEndpoints | null;
  destination?: MoloniEndpoints | null;
  shippingProcess?: IShippingProcess | null;
  config?: IMoloniConfiguration | null;
}

export class MoloniExpeditionDocument implements IMoloniExpeditionDocument {
  constructor(
    public id?: number,
    public origin?: MoloniEndpoints | null,
    public destination?: MoloniEndpoints | null,
    public shippingProcess?: IShippingProcess | null,
    public config?: IMoloniConfiguration | null
  ) {}
}

export function getMoloniExpeditionDocumentIdentifier(moloniExpeditionDocument: IMoloniExpeditionDocument): number | undefined {
  return moloniExpeditionDocument.id;
}
