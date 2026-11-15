import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { EmployeeListComponent } from './components/employee-list-component/employee-list-component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, EmployeeListComponent],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {
  greetingMessage: string = 'Hello World!';
  protected readonly title = signal('employee-ui');
}
