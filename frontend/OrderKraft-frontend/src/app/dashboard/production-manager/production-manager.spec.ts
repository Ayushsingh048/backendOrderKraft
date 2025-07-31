import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ProductionManagerPage } from './production-manager';

describe('ProductionManagerPage', () => {
  let component: ProductionManagerPage;
  let fixture: ComponentFixture<ProductionManagerPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProductionManagerPage]
    }).compileComponents();

    fixture = TestBed.createComponent(ProductionManagerPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
