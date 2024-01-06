import { Authority } from './authority';

export class User {
  public id: string = '';
  public username: string = '';
  public password: string = '';
  public authorities!: Authority[];
}
