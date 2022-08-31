import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {DashboardComponent} from "./components/dashboard/dashboard.component";
import {OrganisationComponent} from "./components/organisation/organisation.component";

const routes: Routes = [
  {path:'',component:DashboardComponent},
  {path:'Dashboard',component:DashboardComponent},
  {path:'Organisation',component:OrganisationComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
