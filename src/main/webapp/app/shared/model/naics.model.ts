export interface INaics {
  id?: number;
  description?: string;
  code?: string;
  title?: string;
}

export class Naics implements INaics {
  constructor(public id?: number, public description?: string, public code?: string, public title?: string) {}
}
