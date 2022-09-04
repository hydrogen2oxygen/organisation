export class Organisation {
  name:string = '';
  groups:Group[] = [];
  locations:Location[] = [];
  personList:Person[] = [];
}

export class Group {
  name:string = '';
  persons:Person[] = [];
  locations:Location[] = [];
  tags:string[] = [];
}

export class Person {
  lastName:string = '';
  firstName:string = '';
  contactData:ContactData = new ContactData();
  address:Address = new Address();
  birthDate:Date|undefined;
  age:number|undefined;
  tags:string[] = [];
  groupLead:boolean = false;
  groupLeadAssistant:boolean = false;
}

export class ContactData {
  phoneNumber:string|undefined;
  cellPhone:string|undefined;
  email:string|undefined;
  contactPersons:string[] = [];
}

export class Address {
  position:Position|undefined;
  street:string|undefined;
  houseNumber:string|undefined;
  postalCode:string|undefined;
  city:string|undefined;
  countryIsoCode3:string|undefined;
}

export class Position {
  x:string|undefined;
  y:string|undefined;
}

export class Location {
  name:string = '';
  purpose:string|undefined;
  address:Address = new Address();
  tags:string[] = [];
}
