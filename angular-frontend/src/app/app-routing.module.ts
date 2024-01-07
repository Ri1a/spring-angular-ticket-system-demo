import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OverviewComponent } from './component/ticket/ticket-overview/overview.component';
import { LogInComponent } from './component/log-in/log-in.component';
import { SettingsComponent } from './component/settings/settings.component';
import { AuthGuardService } from './services/auth-guard.service';
import { RoleGuardService } from './services/role-guard.service';
import { AboutComponent } from './component/about/about.component';
import { ProjectComponent } from './component/project/project-overview/project.component';
import { PageNotFoundComponent } from './component/page-not-found/page-not-found.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/project',
    pathMatch: 'full',
  },
  {
    path: 'overview/:id',
    component: OverviewComponent,
    canActivate: [AuthGuardService],
  },
  { path: 'login', component: LogInComponent },
  {
    path: 'settings',
    component: SettingsComponent,
    canActivate: [AuthGuardService, RoleGuardService],
  },
  { path: 'about', component: AboutComponent },
  {
    path: 'project',
    component: ProjectComponent,
    canActivate: [AuthGuardService],
  },
  { path: '**', component: PageNotFoundComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
