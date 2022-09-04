import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './components/app/app.component';
import {HttpClientModule} from "@angular/common/http";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {ReactiveFormsModule} from "@angular/forms";
import {ToastrModule} from "ngx-toastr";
import { OrganisationComponent } from './components/organisation/organisation.component';
import { MapComponent } from './components/map/map.component';
import { DiagramComponent } from './components/diagram/diagram.component';
import { ImportDataComponent } from './components/import-data/import-data.component';

@NgModule({
  declarations: [
    AppComponent,
    OrganisationComponent,
    MapComponent,
    DiagramComponent,
    ImportDataComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    ToastrModule.forRoot()
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
