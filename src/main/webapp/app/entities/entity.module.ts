import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'opportunity',
        loadChildren: () => import('./opportunity/opportunity.module').then(m => m.SandboxOpportunityModule),
      },
      {
        path: 'naics',
        loadChildren: () => import('./naics/naics.module').then(m => m.SandboxNaicsModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class SandboxEntityModule {}
