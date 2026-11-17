import { Component, OnInit, signal } from '@angular/core';
import { Employee } from '../../classes/employee';
import { EmployeeService } from '../../service/employee-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-employee-list-component',
  imports: [],
  templateUrl: './employee-list-component.html',
  styleUrl: './employee-list-component.css',
})
export class ListEmployeeComponent implements OnInit {
  employees = signal<Employee[]>([]);

  constructor(
    private readonly employeeService: EmployeeService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.employeeService.getAllEmployees().subscribe((employees) => this.employees.set(employees));
  }

  updateEmployee(employeeId?: number) {
    this.router.navigate(['updateEmployee', employeeId]);
  }
}
