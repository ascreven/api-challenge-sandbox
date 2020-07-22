import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SandboxSharedModule } from 'app/shared/shared.module';
import { DIRECTORY_ROUTE } from './directory.route';
import { DirectoryComponent } from './directory.component';

@NgModule({
  imports: [SandboxSharedModule, RouterModule.forChild([DIRECTORY_ROUTE])],
  declarations: [DirectoryComponent],
})
export class SandboxDirectoryModule {}
