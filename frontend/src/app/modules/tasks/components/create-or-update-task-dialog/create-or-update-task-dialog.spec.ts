import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateOrUpdateTaskDialog } from './create-or-update-task-dialog';

describe('CreateOrUpdateTaskDialog', () => {
  let component: CreateOrUpdateTaskDialog;
  let fixture: ComponentFixture<CreateOrUpdateTaskDialog>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateOrUpdateTaskDialog]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateOrUpdateTaskDialog);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
