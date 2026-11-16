import { Address } from './address';

export class Employee {
  constructor(
    public id: number = 0,
    public firstName: string = '',
    public lastName: string = '',
    public age: number = 0,
    public email: string = '',
    public address: Address = new Address(),
  ) {}
}
