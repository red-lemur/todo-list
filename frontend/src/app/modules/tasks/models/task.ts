export class Task {
  id?: number;
  label: string;
  description: string;
  completed: boolean;

  constructor(id: number, label: string, description: string = '', completed: boolean = false) {
    this.id = id;
    this.label = label;
    this.description = description;
    this.completed = completed;
  }
}
