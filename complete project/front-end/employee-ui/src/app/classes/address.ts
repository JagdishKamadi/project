export class Address {
  constructor(
    public id?: number,
    public houseNumber: string = '',
    public buildingName: string = '',
    public street: string = '',
    public landmark: string = '',
    public locality: string = '',
    public village: string = '',
    public city: string = '',
    public district: string = '',
    public state: string = '',
    public country: string = '',
    public pincode: string = '',
    public addressType: string = '',
  ) {}
}
