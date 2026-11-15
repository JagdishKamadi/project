import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Employee } from '../classes/employee';

@Injectable({
  providedIn: 'root',
})
export class EmployeeService {
  private baseURl: string = 'http://localhost:8080/v1/employees/all';
  constructor(private httpClient: HttpClient) {}

  public getEmployeeList(): Observable<Employee[]> {
    return this.httpClient.get<Employee[]>(this.baseURl);
  }
}
