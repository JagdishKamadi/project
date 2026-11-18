import { Routes } from '@angular/router';
import { CreateEmployeeComponent } from './components/create-employee-component/create-employee-component';
import { DeleteEmployeeComponent } from './components/delete-employee-component/delete-employee-component';
import { ListEmployeeComponent } from './components/employee-list-component/employee-list-component';
import { UpdateEmployeeComponent } from './components/update-employee-component/update-employee-component';
import { ViewEmployeeComponent } from './components/view-employee-component/view-employee-component';

export const routes: Routes = [
  { path: 'employees', component: ListEmployeeComponent },
  { path: 'createEmployee', component: CreateEmployeeComponent },
  { path: 'updateEmployee/:employeeId', component: UpdateEmployeeComponent },
  { path: 'deleteEmployee/:employeeId', component: DeleteEmployeeComponent },
  { path: 'viewEmployee/:employeeId', component: ViewEmployeeComponent },
  { path: '', redirectTo: 'employees', pathMatch: 'full' },
];
