import { IMoloniConfiguration } from 'app/entities/moloni-configuration/moloni-configuration.model';
import { MoloniEndpoints } from 'app/entities/enumerations/moloni-endpoints.model';
import { DocumentType } from 'app/entities/enumerations/document-type.model';

export interface IMoloniReceptionDocument {
  id?: number;
  origin?: MoloniEndpoints | null;
  destination?: MoloniEndpoints | null;
  documentType?: DocumentType | null;
  config?: IMoloniConfiguration | null;
}

export class MoloniReceptionDocument implements IMoloniReceptionDocument {
  constructor(
    public id?: number,
    public origin?: MoloniEndpoints | null,
    public destination?: MoloniEndpoints | null,
    public documentType?: DocumentType | null,
    public config?: IMoloniConfiguration | null
  ) {}
}

export function getMoloniReceptionDocumentIdentifier(moloniReceptionDocument: IMoloniReceptionDocument): number | undefined {
  return moloniReceptionDocument.id;
}
