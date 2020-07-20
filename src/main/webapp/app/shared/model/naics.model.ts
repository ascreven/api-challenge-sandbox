import { IOpportunity } from 'app/shared/model/opportunity.model';

export interface INaics {
  id?: number;
  description?: string;
  code?: string;
  title?: string;
  opportunities?: IOpportunity[];
}

export class Naics implements INaics {
  constructor(
    public id?: number,
    public description?: string,
    public code?: string,
    public title?: string,
    public opportunities?: IOpportunity[]
  ) {}
}
