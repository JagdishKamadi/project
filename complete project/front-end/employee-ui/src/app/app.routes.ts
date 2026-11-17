import { Routes } from '@angular/router';
import { ListEmployeeComponent } from './components/employee-list-component/employee-list-component';
import { CreateEmployeeComponent } from './components/create-employee-component/create-employee-component';
import { UpdateEmployeeComponent } from './components/update-employee-component/update-employee-component';

export const routes: Routes = [
  { path: 'employees', component: ListEmployeeComponent },
  { path: 'createEmployee', component: CreateEmployeeComponent },
  { path: 'updateEmployee/:employeeId', component: UpdateEmployeeComponent },
  { path: '', redirectTo: 'employees', pathMatch: 'full' },
];
