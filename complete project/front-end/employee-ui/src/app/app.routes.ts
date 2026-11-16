import { Routes } from '@angular/router';
import { EmployeeListComponent } from './components/employee-list-component/employee-list-component';

export const routes: Routes = [
  { path: 'employees', component: EmployeeListComponent },
  { path: '', redirectTo: 'employees', pathMatch: 'full' },
];
