import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Employee } from '../../classes/employee';
import { EmployeeService } from '../../service/employee-service';

@Component({
  selector: 'app-create-employee-component',
  imports: [FormsModule],
  templateUrl: './create-employee-component.html',
  styleUrl: './create-employee-component.css',
})
export class CreateEmployeeComponent {
  employee: Employee = new Employee();

  constructor(
    private readonly employeeService: EmployeeService,
    private readonly router: Router,
  ) {}

  onSubmit(): void {
    this.employeeService.createEmployee(this.employee).subscribe({
      next: () => this.navigateToEmployeeList(),
      error: (error) => console.error('Failed to create employee', error),
    });
  }

  private navigateToEmployeeList(): void {
    this.router.navigate(['/employees']);
  }
}
