import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Footer } from './footer/footer';
import { FormsModule } from '@angular/forms'; 
@Component({
  selector: 'app-root',
  imports: [RouterOutlet,Footer,FormsModule],


  templateUrl: './app.html',
  styleUrl: './app.css'
})


export class App {
  protected readonly title = signal('OrderKraft-frontend');
}
