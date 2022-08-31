import { Component, OnInit } from '@angular/core';
import {OrganisationService} from "../../service/organisation.service";
import {Group, Organisation} from "../../domains/Organisation";
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-organisation',
  templateUrl: './organisation.component.html',
  styleUrls: ['./organisation.component.scss']
})
export class OrganisationComponent implements OnInit {

  org: Organisation = new Organisation();
  groupForm: FormGroup = this.initForm();

  constructor(private organisationService: OrganisationService,
              private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
    this.organisationService.loadOrganisation().subscribe(o => this.org = o);
  }


  addNewGroup() {
    let newGroup = new Group();
    newGroup.name = this.groupForm.get('name')?.value;
    this.org.groups.push(newGroup);
    this.organisationService.saveOrganisation(this.org).subscribe(o => this.org = o);
  }

  private initForm(): FormGroup {
    return this.formBuilder.group({
      name: ''
    });
  }
}
