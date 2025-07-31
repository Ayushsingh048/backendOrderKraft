import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Footer } from './footer/footer';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet,Footer],
<<<<<<< HEAD
=======

>>>>>>> 669378b54fcc2e5482096f7bee5225faa5aba57b
  templateUrl: './app.html',
  styleUrl: './app.css'
})


export class App {
  protected readonly title = signal('OrderKraft-frontend');
}
