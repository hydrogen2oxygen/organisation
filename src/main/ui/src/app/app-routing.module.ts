import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {OrganisationComponent} from "./components/organisation/organisation.component";
import {MapComponent} from "./components/map/map.component";
import {DiagramComponent} from "./components/diagram/diagram.component";

const routes: Routes = [
  {path:'',component:OrganisationComponent},
  {path:'Organisation',component:OrganisationComponent},
  {path:'Map',component:MapComponent},
  {path:'Diagram',component:DiagramComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
