import { Component, signal } from '@angular/core';
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';
import { EmployeeListComponent } from './components/employee-list-component/employee-list-component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, EmployeeListComponent, RouterLink, RouterLinkActive],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {
  greetingMessage: string = 'Hello World!';
  protected readonly title = signal('employee-ui');
}
