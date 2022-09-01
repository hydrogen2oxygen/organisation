import {Component, OnInit} from '@angular/core';
import {OrganisationService} from "../../service/organisation.service";
import {Group, Organisation, Person} from "../../domains/Organisation";
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-organisation',
  templateUrl: './organisation.component.html',
  styleUrls: ['./organisation.component.scss']
})
export class OrganisationComponent implements OnInit {

  org: Organisation = new Organisation();
  groupForm: FormGroup = this.initForm();
  group:Group|undefined;

  constructor(private organisationService: OrganisationService,
              private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
    this.organisationService.loadOrganisation().subscribe(o => {
      this.org = o;
      this.groupForm = this.initForm();
    });
  }

  addNewGroup() {
    let newGroup = new Group();
    newGroup.name = this.groupForm.get('name')?.value;
    this.org.groups.push(newGroup);
    this.saveOrg();
  }

  editGroup(group: Group) {
    this.group = group;
    this.groupForm.get('name')?.setValue(group.name);
  }


  saveGroup(group: Group) {
    group.name = this.groupForm.get('name')?.value;
    this.saveOrg();
  }

  removeGroup(group: Group) {
    this.removeGroupFromArray(group);
    this.organisationService.saveOrganisation(this.org).subscribe(o => this.org = o);
  }

  clearEditMode() {
    this.group = undefined;
    this.groupForm = this.initForm();
  }

  addNewPerson(group: Group) {
    this.group = group;
  }

  saveOrgName() {
    this.org.name = this.groupForm.get('mainOrganisationName')?.value;
    this.saveOrg();
  }

  private saveOrg() {
    this.organisationService.saveOrganisation(this.org).subscribe(o => {
      this.org = o;
      this.groupForm = this.initForm();
    });
  }

  private removeGroupFromArray(group: Group) {
    const index = this.org.groups.indexOf(group, 0);
    if (index > -1) {
      this.org.groups.splice(index, 1);
    }
  }

  private initForm(): FormGroup {
    return this.formBuilder.group({
      mainOrganisationName: this.org.name,
      name: '',
      newPersonName: '',
      street: '',
      houseNumber: '',
      postalCode: '',
      city: '',
      cellPhone: ''
    });
  }

  addPersonToGroup() {
    if (this.group) {
      let newPerson = new Person();
      newPerson.name = this.groupForm.get("newPersonName")?.value;
      newPerson.address.street = this.groupForm.get("street")?.value;
      newPerson.address.houseNumber = this.groupForm.get("houseNumber")?.value;
      this.group.persons.push(newPerson);
      this.saveOrg();
    }
  }
}
