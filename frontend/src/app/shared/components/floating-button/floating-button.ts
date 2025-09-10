import { Component, Input } from '@angular/core';
import { Button } from '../button/button';
import { MatButtonModule } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';

@Component({
  selector: 'app-floating-button',
  imports: [MatButtonModule, MatIcon],
  templateUrl: './floating-button.html',
  styleUrl: './floating-button.scss',
})
export class FloatingButton extends Button {
  @Input({ required: true }) iconName!: string;
}
