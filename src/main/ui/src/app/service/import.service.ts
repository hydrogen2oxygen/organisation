import { Injectable } from '@angular/core';
import {HttpClient, HttpEvent, HttpParams, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";
import {ImportHeaderInfo} from "../domains/ImportHeaderInfo";
import {KeyValueMap} from "../domains/KeyValueMap";

@Injectable({
  providedIn: 'root'
})
export class ImportService {

  public static baseUrl = '';

  constructor(private http:HttpClient) {
    ImportService.baseUrl = `http://localhost:7000/organisation-import`;
  }

  uploadFile(file: File): Observable<ImportHeaderInfo> {

    let formData = new FormData();
    formData.append('upload', file);

    let params = new HttpParams();

    const options = {
      params: params,
      reportProgress: true,
    };

    return this.http.post<ImportHeaderInfo>(`${ImportService.baseUrl}-header`, formData, options);
  }

  importData(map: KeyValueMap): Observable<void> {
    console.log(map)
    return this.http.post<void>(`${ImportService.baseUrl}-data`, map);
  }

  importGeoLocations(): Observable<void> {
    return this.http.post<void>(`${ImportService.baseUrl}-geo-locations`,null);
  }
}
