import { Component, OnInit, signal } from '@angular/core';
import { Employee } from '../../classes/employee';
import { EmployeeService } from '../../service/employee-service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-view-employee-component',
  imports: [],
  templateUrl: './view-employee-component.html',
  styleUrl: './view-employee-component.css',
})
export class ViewEmployeeComponent implements OnInit {
  employeeId: number = -1;
  employee = signal<Employee>(new Employee());

  constructor(
    private readonly employeeService: EmployeeService,
    private readonly activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.employeeId = Number(this.activatedRoute.snapshot.params['employeeId']);
    this.employeeService.getEmployee(this.employeeId).subscribe((data) => {
      this.employee.set(data);
    });
  }
}
