import { IMoloniReceptionDocument } from 'app/entities/moloni-reception-document/moloni-reception-document.model';
import { IMoloniExpeditionDocument } from 'app/entities/moloni-expedition-document/moloni-expedition-document.model';

export interface IMoloniConfiguration {
  id?: number;
  username?: string | null;
  password?: string | null;
  company?: string | null;
  client?: string | null;
  secret?: string | null;
  receptions?: IMoloniReceptionDocument[] | null;
  expeditions?: IMoloniExpeditionDocument[] | null;
}

export class MoloniConfiguration implements IMoloniConfiguration {
  constructor(
    public id?: number,
    public username?: string | null,
    public password?: string | null,
    public company?: string | null,
    public client?: string | null,
    public secret?: string | null,
    public receptions?: IMoloniReceptionDocument[] | null,
    public expeditions?: IMoloniExpeditionDocument[] | null
  ) {}
}

export function getMoloniConfigurationIdentifier(moloniConfiguration: IMoloniConfiguration): number | undefined {
  return moloniConfiguration.id;
}
