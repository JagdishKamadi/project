import { Routes } from '@angular/router';
import { ListEmployeeComponent } from './components/employee-list-component/employee-list-component';
import { CreateEmployeeComponent } from './components/create-employee-component/create-employee-component';

export const routes: Routes = [
  { path: 'employees', component: ListEmployeeComponent },
  { path: 'createEmployee', component: CreateEmployeeComponent },
  { path: '', redirectTo: 'employees', pathMatch: 'full' },
];
