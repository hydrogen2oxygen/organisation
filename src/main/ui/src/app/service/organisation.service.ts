import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Organisation} from "../domains/Organisation";

@Injectable({
  providedIn: 'root'
})
export class OrganisationService {

  public static baseUrl = '';

  constructor(private http:HttpClient) {
    OrganisationService.baseUrl = `http://localhost:7000/organisation`;
  }

  loadOrganisation():Observable<Organisation> {

    return this.http.get<Organisation>(OrganisationService.baseUrl);
  }

  saveOrganisation(org: Organisation) :Observable<Organisation> {
    return this.http.post<Organisation>(OrganisationService.baseUrl, org);
  }
}
