import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { NewTicketComponent } from './component/ticket/new-ticket/new-ticket.component';

import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { OverviewComponent } from './component/ticket/ticket-overview/overview.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { MatSliderModule } from '@angular/material/slider';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatCardModule } from '@angular/material/card';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import {
  MatDatepicker,
  MatDatepickerModule,
} from '@angular/material/datepicker';
import { MatNativeDateModule, MatOptionModule } from '@angular/material/core';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LogInComponent } from './component/log-in/log-in.component';
import { RouterModule } from '@angular/router';
import { AppRoutingModule } from './app-routing.module';
import { SettingsComponent } from './component/settings/settings.component';
import { NewUserComponent } from './component/new-user/new-user.component';
import { BasicAuthInterceptorService } from './services/basic-auth-interceptor.service';
import { AboutComponent } from './component/about/about.component';
import { MatTabsModule } from '@angular/material/tabs';
import { ProjectComponent} from "./component/project/project-overview/project.component";
import { NewProjectComponent } from './component/project/new-project/new-project.component';
import { LayoutModule } from '@angular/cdk/layout';

@NgModule({
  declarations: [
    AppComponent,
    NewTicketComponent,
    OverviewComponent,
    LogInComponent,
    SettingsComponent,
    NewUserComponent,
    AboutComponent,
    ProjectComponent,
    NewProjectComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatSliderModule,
    MatGridListModule,
    MatToolbarModule,
    MatIconModule,
    MatMenuModule,
    MatCardModule,
    MatDialogModule,
    DragDropModule,
    MatFormFieldModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatInputModule,
    MatSelectModule,
    MatOptionModule,
    MatButtonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forRoot([]),
    AppRoutingModule,
    MatTabsModule,
    LayoutModule,
  ],
  providers: [
    {
      provide: MatDialogRef,
      useValue: {},
    },
    {
      provide: MatDatepicker,
      useValue: {},
    },
    {
      // token interceptor
      provide: HTTP_INTERCEPTORS,
      useClass: BasicAuthInterceptorService,
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
