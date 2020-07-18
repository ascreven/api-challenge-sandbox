import { Moment } from 'moment';

export interface IOpportunity {
  id?: number;
  title?: string;
  solNum?: string;
  noticeid?: string;
  state?: string;
  zip?: string;
  postedFrom?: Moment;
  postedTo?: Moment;
  reponseDeadLine?: string;
  classificationCode?: string;
  naicsCode?: string;
}

export class Opportunity implements IOpportunity {
  constructor(
    public id?: number,
    public title?: string,
    public solNum?: string,
    public noticeid?: string,
    public state?: string,
    public zip?: string,
    public postedFrom?: Moment,
    public postedTo?: Moment,
    public reponseDeadLine?: string,
    public classificationCode?: string,
    public naicsCode?: string
  ) {}
}
