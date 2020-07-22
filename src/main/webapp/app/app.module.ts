import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { SandboxSharedModule } from 'app/shared/shared.module';
import { SandboxCoreModule } from 'app/core/core.module';
import { SandboxAppRoutingModule } from './app-routing.module';
import { SandboxHomeModule } from './home/home.module';
import { SandboxEntityModule } from './entities/entity.module';
import { SandboxDirectoryModule } from './directory/directory.module';
import { SandboxDashboardModule } from './dashboard/dashboard.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    SandboxSharedModule,
    SandboxCoreModule,
    SandboxHomeModule,
    SandboxDirectoryModule,
    SandboxDashboardModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    SandboxEntityModule,
    SandboxAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class SandboxAppModule {}
