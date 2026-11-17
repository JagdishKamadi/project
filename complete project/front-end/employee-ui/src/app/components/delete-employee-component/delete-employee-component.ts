import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { EmployeeService } from '../../service/employee-service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-delete-employee-component',
  imports: [FormsModule],
  templateUrl: './delete-employee-component.html',
  styleUrl: './delete-employee-component.css',
})
export class DeleteEmployeeComponent implements OnInit {
  employeeId: number = -1;
  constructor(
    private readonly employeeService: EmployeeService,
    private readonly activatedRoute: ActivatedRoute,
    private readonly router: Router,
  ) {}

  ngOnInit(): void {
    this.employeeId = Number(this.activatedRoute.snapshot.params['employeeId']);
  }

  onSubmit() {
    this.employeeService.deleteEmployee(this.employeeId).subscribe({
      next: () => this.goToEmployeeList(),
      error: (err) => console.error('Failed to delete employee', err),
    });
  }

  goToEmployeeList(): void {
    this.router.navigate(['employees']);
  }
}
