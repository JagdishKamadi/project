import { Component, signal } from '@angular/core';
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';
import { ListEmployeeComponent } from './components/employee-list-component/employee-list-component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, ListEmployeeComponent, RouterLink, RouterLinkActive],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {
  greetingMessage: string = 'Hello World!';
  protected readonly title = signal('employee-ui');
}
