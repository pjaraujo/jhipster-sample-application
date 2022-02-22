import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ShippingProcessDetailComponent } from './shipping-process-detail.component';

describe('ShippingProcess Management Detail Component', () => {
  let comp: ShippingProcessDetailComponent;
  let fixture: ComponentFixture<ShippingProcessDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ShippingProcessDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ shippingProcess: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ShippingProcessDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ShippingProcessDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load shippingProcess on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.shippingProcess).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
