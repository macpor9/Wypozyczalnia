export class User {
  name: string
  surname: string
  email: string
  password: string
  repeatPassword: string

  constructor(name: string, surname: string, email: string, password: string, repeatPassword: string) {
    this.name = name;
    this.surname = surname;
    this.email = email;
    this.password = password;
    this.repeatPassword = repeatPassword;
  }


}
