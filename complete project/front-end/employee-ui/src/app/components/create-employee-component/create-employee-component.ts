import { Component } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Employee } from '../../classes/employee';
import { EmployeeService } from '../../service/employee-service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-create-employee-component',
  imports: [FormsModule],
  templateUrl: './create-employee-component.html',
  styleUrl: './create-employee-component.css',
})
export class CreateEmployeeComponent {
  employee: Employee = new Employee();

  constructor(
    private employeeService: EmployeeService,
    private router: Router,
  ) {}

  private saveEmployee(): void {
    this.employeeService.createEmployee(this.employee).subscribe({
      next: (data) => {
        console.log(data);
        this.goToEmployeeListPage();
      },
      error: (err) => console.log(err),
    });
  }

  private goToEmployeeListPage(): void {
    this.router.navigate(['/employees']);
  }

  public onSubmit(): void {
    console.log('Employee Data', this.employee);
    this.saveEmployee();
  }
}
