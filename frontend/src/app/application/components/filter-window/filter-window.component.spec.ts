import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FilterWindowComponent } from './filter-window.component';

describe('FilterWindowComponent', () => {
  let component: FilterWindowComponent;
  let fixture: ComponentFixture<FilterWindowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FilterWindowComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FilterWindowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
