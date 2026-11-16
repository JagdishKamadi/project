import { Address } from './address';

export class Employee {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public age?: number,
    public email?: string,
    public address: Address = new Address(),
  ) {}
}
