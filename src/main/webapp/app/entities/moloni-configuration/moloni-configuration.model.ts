import { IMoloniDocumentType } from 'app/entities/moloni-document-type/moloni-document-type.model';

export interface IMoloniConfiguration {
  id?: number;
  username?: string | null;
  password?: string | null;
  company?: string | null;
  client?: string | null;
  secret?: string | null;
  receptionIns?: IMoloniDocumentType[] | null;
  receptionOuts?: IMoloniDocumentType[] | null;
  expeditionIns?: IMoloniDocumentType[] | null;
  expeditionOuts?: IMoloniDocumentType[] | null;
}

export class MoloniConfiguration implements IMoloniConfiguration {
  constructor(
    public id?: number,
    public username?: string | null,
    public password?: string | null,
    public company?: string | null,
    public client?: string | null,
    public secret?: string | null,
    public receptionIns?: IMoloniDocumentType[] | null,
    public receptionOuts?: IMoloniDocumentType[] | null,
    public expeditionIns?: IMoloniDocumentType[] | null,
    public expeditionOuts?: IMoloniDocumentType[] | null
  ) {}
}

export function getMoloniConfigurationIdentifier(moloniConfiguration: IMoloniConfiguration): number | undefined {
  return moloniConfiguration.id;
}
