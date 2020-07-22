import { Route } from '@angular/router';

import { DirectoryComponent } from './directory.component';

export const DIRECTORY_ROUTE: Route = {
  path: '',
  component: DirectoryComponent,
  data: {
    authorities: [],
    pageTitle: 'Welcome, Java Hipster!',
  },
};
