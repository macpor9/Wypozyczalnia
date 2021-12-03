import {Role} from "./Role";

export class Account {
  id: string;
  name: string;
  surname: string;
  roles: Array<string>;

  constructor(id: string, name: string, surname: string, role: Array<string>) {
    this.id = id;
    this.name = name;
    this.surname = surname;
    this.roles = role;
  }

}
