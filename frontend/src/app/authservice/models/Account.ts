export class Account {
  id: string;
  facebookId: string;
  name: string;
  token?: string;

  constructor(id: string, facebookId: string, name: string, token: string) {
    this.id = id;
    this.facebookId = facebookId;
    this.name = name;
    this.token = token;
  }
}
