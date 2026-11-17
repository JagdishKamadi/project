import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Address } from '../../classes/address';
import { Employee } from '../../classes/employee';
import { EmployeeService } from '../../service/employee-service';

@Component({
  selector: 'app-update-employee-component',
  imports: [FormsModule],
  templateUrl: './update-employee-component.html',
  styleUrl: './update-employee-component.css',
})
export class UpdateEmployeeComponent implements OnInit {
  employee: Employee = new Employee();
  employeeId: number = 0;

  constructor(
    private readonly employeeService: EmployeeService,
    private readonly activatedRoute: ActivatedRoute,
    private readonly router: Router,
    private readonly changeDetectorRef: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    this.employeeId = Number(this.activatedRoute.snapshot.params['employeeId']);
    this.employeeService.getEmployee(this.employeeId).subscribe({
      next: (data) => {
        this.employee = { ...data, address: data.address ?? new Address() };
        this.changeDetectorRef.markForCheck();
      },
      error: (err) => console.error('Failed to load employee', err),
    });
  }

  onSubmit(): void {
    console.log('Calling onSubmit() method on update employee event');
    this.employeeService.updateEmployee(this.employeeId, this.employee).subscribe({
      next: (data) => {
        console.log(data);
        this.goToEmployeeList();
      },
      error: (err) => console.log(err),
    });
  }

  goToEmployeeList(): void {
    this.router.navigate(['employees']);
  }
}
