import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Employee } from '../../classes/employee';
import { EmployeeService } from '../../service/employee-service';

@Component({
  selector: 'app-create-employee-component',
  imports: [ReactiveFormsModule],
  templateUrl: './create-employee-component.html',
  styleUrl: './create-employee-component.css',
})
export class CreateEmployeeComponent {
  employee: Employee = new Employee();
  employeeForm = new FormGroup({
    firstname: new FormControl(''),
    lastname: new FormControl(''),
    email: new FormControl(''),
    age: new FormControl(''),
  });
  constructor(
    private employeeService: EmployeeService,
    private router: Router,
  ) {}

  private saveEmployee(): void {
    this.employeeService.createEmployee(this.employee).subscribe({
      next: (data) => console.log(data),
      error: (err) => console.log('Error `${err}`'),
    });
    this.goToEmployeeList();
  }

  private goToEmployeeList(): void {
    this.router.navigate(['/employees']);
  }

  public onSubmit(): void {
    const value = this.employeeForm.value;
    this.employee.firstName = value.firstname ?? '';
    this.employee.lastName = value.lastname ?? '';
    this.employee.email = value.email ?? '';
    this.employee.age = Number(value.age);
    console.log(this.employee);
    this.saveEmployee();
  }
}
