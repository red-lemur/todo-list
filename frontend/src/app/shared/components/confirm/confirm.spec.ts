import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Confirm } from './confirm';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

describe('Confirm', () => {
  let component: Confirm;
  let fixture: ComponentFixture<Confirm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Confirm],
      providers: [{ provide: MAT_DIALOG_DATA, useValue: {} }],
    }).compileComponents();

    fixture = TestBed.createComponent(Confirm);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
