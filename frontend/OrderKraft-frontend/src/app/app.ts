import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { LoginPage } from './login-page/login-page';
<<<<<<< HEAD
import { ProductionManagerPage } from './production-manager/production-manager';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet,LoginPage,ProductionManagerPage],
=======
import { Footer } from './footer/footer';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet,LoginPage,Footer],
>>>>>>> 8f6f0c693a8eaaa8a60d88e57284d6acdd2fd137
  templateUrl: './app.html',
  styleUrl: './app.css'
})


export class App {
  protected readonly title = signal('OrderKraft-frontend');
}
