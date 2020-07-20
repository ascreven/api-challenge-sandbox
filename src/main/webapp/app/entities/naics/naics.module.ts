import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SandboxSharedModule } from 'app/shared/shared.module';
import { NaicsComponent } from './naics.component';
import { NaicsDetailComponent } from './naics-detail.component';
import { NaicsUpdateComponent } from './naics-update.component';
import { NaicsDeleteDialogComponent } from './naics-delete-dialog.component';
import { naicsRoute } from './naics.route';

@NgModule({
  imports: [SandboxSharedModule, RouterModule.forChild(naicsRoute)],
  declarations: [NaicsComponent, NaicsDetailComponent, NaicsUpdateComponent, NaicsDeleteDialogComponent],
  entryComponents: [NaicsDeleteDialogComponent],
})
export class SandboxNaicsModule {}
