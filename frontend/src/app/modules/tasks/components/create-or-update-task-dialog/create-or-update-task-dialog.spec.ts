import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateOrUpdateTaskDialog } from './create-or-update-task-dialog';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Task } from '../../models/task';

describe('CreateOrUpdateTaskDialog', () => {
  const taskDetails: Task = new Task(1, 'label', 'description', true);
  const dialogTitle: string = 'Test dialog';

  let component: CreateOrUpdateTaskDialog;
  let fixture: ComponentFixture<CreateOrUpdateTaskDialog>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateOrUpdateTaskDialog],
      providers: [
        {
          provide: MAT_DIALOG_DATA,
          useValue: {
            taskDetails: taskDetails,
            dialogTitle: dialogTitle,
          },
        },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(CreateOrUpdateTaskDialog);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
