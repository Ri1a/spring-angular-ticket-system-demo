import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {OverviewComponent} from "./component/ticket/overview/overview/overview.component";
import {LogInComponent} from "./component/log-in/log-in.component";
import {SettingsComponent} from "./component/settings/settings.component";
import {AuthGuardService} from "./services/auth-guard.service";
import {RoleGuardService} from "./services/role-guard.service";
import {AboutComponent} from "./component/about/about.component";

const routes: Routes = [
  { path: 'overview', component: OverviewComponent,canActivate:[AuthGuardService]  },
  { path: 'login', component: LogInComponent  },
  { path: 'settings', component: SettingsComponent,canActivate:[AuthGuardService, RoleGuardService]},
  { path: 'about', component: AboutComponent  },
  { path: '**', redirectTo: '/login' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
