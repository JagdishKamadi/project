import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, retry } from 'rxjs';
import { Employee } from '../classes/employee';

@Injectable({
  providedIn: 'root',
})
export class EmployeeService {
  private readonly baseUrl = 'http://localhost:8080/v1/employees';

  constructor(private readonly httpClient: HttpClient) {}

  getEmployee(employeeId: number): Observable<Employee> {
    return this.httpClient.get<Employee>(this.baseUrl + '/' + employeeId);
  }

  getAllEmployees(): Observable<Employee[]> {
    return this.httpClient.get<Employee[]>(this.baseUrl);
  }

  createEmployee(employee: Employee): Observable<Employee> {
    return this.httpClient.post<Employee>(this.baseUrl, employee);
  }

  updateEmployee(employeeId: number, employee: Employee): Observable<Employee> {
    return this.httpClient.put<Employee>(this.baseUrl + '/' + employeeId, employee);
  }

  deleteEmployee(employeeId: number): Observable<unknown> {
    return this.httpClient.delete(this.baseUrl + '/' + employeeId, { responseType: 'text' });
  }
}
