export class Task {
  id!: number;
  label: string;
  description: string;
  completed: boolean;

  constructor(label: string, description: string = '', completed: boolean = false) {
    this.label = label;
    this.description = description;
    this.completed = completed;
  }
}
