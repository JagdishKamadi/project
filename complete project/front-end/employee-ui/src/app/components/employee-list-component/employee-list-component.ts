import { Component, OnInit } from '@angular/core';
import { Employee } from '../../classes/employee';

@Component({
  selector: 'app-employee-list-component',
  imports: [],
  templateUrl: './employee-list-component.html',
  styleUrl: './employee-list-component.css',
})
export class EmployeeListComponent implements OnInit {
  employees: Employee[] = [];
  ngOnInit(): void {
    this.employees.push(new Employee(1, 'Jack', 'Admin', 21, 'jack@example.com'));
    this.employees.push(new Employee(2, 'Joy', 'Cena', 20, 'jpy@example.com'));
  }
}
