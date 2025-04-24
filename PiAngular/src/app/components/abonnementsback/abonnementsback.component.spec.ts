import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AbonnementsbackComponent } from './abonnementsback.component';

describe('AbonnementsbackComponent', () => {
  let component: AbonnementsbackComponent;
  let fixture: ComponentFixture<AbonnementsbackComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AbonnementsbackComponent]
    });
    fixture = TestBed.createComponent(AbonnementsbackComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
