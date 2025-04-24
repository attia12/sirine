import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BacktempComponent } from './backtemp.component';

describe('BacktempComponent', () => {
  let component: BacktempComponent;
  let fixture: ComponentFixture<BacktempComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BacktempComponent]
    });
    fixture = TestBed.createComponent(BacktempComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
