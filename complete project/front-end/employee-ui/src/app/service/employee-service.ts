import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Employee } from '../classes/employee';

@Injectable({
  providedIn: 'root',
})
export class EmployeeService {
  private baseGetEmployeesURl: string = 'http://localhost:8080/v1/employees/all';
  private baseCreateEmployeesURl: string = 'http://localhost:8080/v1/employees/add';
  constructor(private httpClient: HttpClient) {}

  public getEmployeeList(): Observable<Employee[]> {
    return this.httpClient.get<Employee[]>(this.baseGetEmployeesURl);
  }

  public createEmployee(employee: Employee): Observable<object> {
    // The default id of 0 makes the backend treat the entity as existing, so omit ids on create.
    const { id: _employeeId, address, ...employeeData } = employee;
    const { id: _addressId, ...addressData } = address;
    return this.httpClient.post(this.baseCreateEmployeesURl, {
      ...employeeData,
      address: addressData,
    });
  }
}
