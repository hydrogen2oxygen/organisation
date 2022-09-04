import { Component, OnInit } from '@angular/core';
import {ImportService} from "../../service/import.service";
import {ImportHeaderInfo} from "../../domains/ImportHeaderInfo";
import {map} from "rxjs";
import {KeyValue, KeyValueMap} from "../../domains/KeyValueMap";

@Component({
  selector: 'app-import-data',
  templateUrl: './import-data.component.html',
  styleUrls: ['./import-data.component.scss']
})
export class ImportDataComponent implements OnInit {

  fileToUpload: File | null = null;
  importHeaderInfo:ImportHeaderInfo = new ImportHeaderInfo();
  map = new Map<object, string>();

  constructor(private importService:ImportService) { }

  ngOnInit(): void {
  }

  importFile($event: Event) {
    console.log($event);
    // @ts-ignore
    this.importService.uploadFile($event.target.files[0]).subscribe( i => this.importHeaderInfo = i );
  }

  importData() {
    let keyValueMap:KeyValueMap = new KeyValueMap();
    this.map.forEach((value, key) => {
      let keyValue = new KeyValue();
      keyValue.key = value;
      keyValue.value = JSON.stringify(key);
      keyValueMap.keyValueList.push(keyValue);
    })
    this.importService.importData(keyValueMap).subscribe(()=>{});
  }

  setHeaderFor($event: Event, h:string) {
    // @ts-ignore
    let value = $event.target.value;
    this.map.set(value, h);
    console.log(this.map);
  }
}
