import { Component, OnInit, signal } from '@angular/core';
import { Employee } from '../../classes/employee';
import { EmployeeService } from '../../service/employee-service';

@Component({
  selector: 'app-employee-list-component',
  imports: [],
  templateUrl: './employee-list-component.html',
  styleUrl: './employee-list-component.css',
})
export class ListEmployeeComponent implements OnInit {
  employees = signal<Employee[]>([]);

  constructor(private employeeService: EmployeeService) {}

  ngOnInit(): void {
    this.employeeService.getEmployeeList().subscribe((data) => this.employees.set(data));
  }
}
