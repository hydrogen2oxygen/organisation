import {Component, OnInit} from '@angular/core';
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
  editGroupFlag:boolean = false;

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
    this.organisationService.saveOrganisation(this.org).subscribe(o => {
      this.org = o;
      this.groupForm.get('name')?.setValue('');
    });
  }

  editGroup(group: Group) {

    this.clearEditMode();
    if (group.editMode) {
      group.editMode = false;
      this.editGroupFlag = false;
    } else {
      group.editMode = true;
      this.editGroupFlag = true;
    }

    this.groupForm.get('name')?.setValue(group.name);
  }

  updateGroup(group: Group) {
    group.name = this.groupForm.get('name')?.value;
    this.organisationService.saveOrganisation(this.org).subscribe(o => {
      this.org = o;
      this.editGroupFlag = false;
      this.groupForm.get('name')?.setValue('');
    });
  }

  removeGroup(group: Group) {
    this.removeGroupFromArray(group);
    this.organisationService.saveOrganisation(this.org).subscribe(o => this.org = o);
  }

  private removeGroupFromArray(group: Group) {
    const index = this.org.groups.indexOf(group, 0);
    if (index > -1) {
      this.org.groups.splice(index, 1);
    }
  }

  private initForm(): FormGroup {
    return this.formBuilder.group({
      name: ''
    });
  }

  clearEditMode() {
    this.org.groups.forEach(g => {
      g.editMode = false;
    })
  }
}
