import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RentHistoryDetailsComponent } from './rent-history-details.component';

describe('RentHistoryDetailsComponent', () => {
  let component: RentHistoryDetailsComponent;
  let fixture: ComponentFixture<RentHistoryDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RentHistoryDetailsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RentHistoryDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
