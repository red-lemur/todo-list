import { Component, Input } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatChipsModule } from '@angular/material/chips';
import { MatProgressBarModule } from '@angular/material/progress-bar';

@Component({
  selector: 'app-card',
  imports: [MatCardModule, MatChipsModule, MatProgressBarModule],
  templateUrl: './card.html',
  styleUrl: './card.scss',
})
export class Card {
  @Input() title: string = '';
  @Input() content: string = '';
}
