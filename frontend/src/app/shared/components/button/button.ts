import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-button',
  imports: [MatButtonModule],
  templateUrl: './button.html',
  styleUrl: './button.scss',
})
export class Button {
  @Input({ required: true }) id!: string;
  @Input({ required: true }) label!: string;

  @Output() actionPerformed: EventEmitter<string> = new EventEmitter<string>();

  public onClick(): void {
    this.actionPerformed.emit(this.id);
  }
}
