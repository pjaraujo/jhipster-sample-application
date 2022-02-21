import { IMoloniConfiguration } from 'app/entities/moloni-configuration/moloni-configuration.model';
import { DocumentType } from 'app/entities/enumerations/document-type.model';

export interface IMoloniDocumentType {
  id?: number;
  documentType?: DocumentType | null;
  config1?: IMoloniConfiguration | null;
  config2?: IMoloniConfiguration | null;
  config3?: IMoloniConfiguration | null;
  config4?: IMoloniConfiguration | null;
}

export class MoloniDocumentType implements IMoloniDocumentType {
  constructor(
    public id?: number,
    public documentType?: DocumentType | null,
    public config1?: IMoloniConfiguration | null,
    public config2?: IMoloniConfiguration | null,
    public config3?: IMoloniConfiguration | null,
    public config4?: IMoloniConfiguration | null
  ) {}
}

export function getMoloniDocumentTypeIdentifier(moloniDocumentType: IMoloniDocumentType): number | undefined {
  return moloniDocumentType.id;
}
