import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProcurementOfficer } from './procurement-officer';

describe('ProcurementOfficer', () => {
  let component: ProcurementOfficer;
  let fixture: ComponentFixture<ProcurementOfficer>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProcurementOfficer]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProcurementOfficer);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
