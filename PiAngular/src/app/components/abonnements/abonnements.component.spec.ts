import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AbonnementsComponent } from './abonnements.component';

describe('AbonnementsComponent', () => {
  let component: AbonnementsComponent;
  let fixture: ComponentFixture<AbonnementsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AbonnementsComponent]
    });
    fixture = TestBed.createComponent(AbonnementsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
