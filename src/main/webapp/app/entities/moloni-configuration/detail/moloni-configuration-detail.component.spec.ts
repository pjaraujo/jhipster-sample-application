import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MoloniConfigurationDetailComponent } from './moloni-configuration-detail.component';

describe('MoloniConfiguration Management Detail Component', () => {
  let comp: MoloniConfigurationDetailComponent;
  let fixture: ComponentFixture<MoloniConfigurationDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MoloniConfigurationDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ moloniConfiguration: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(MoloniConfigurationDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(MoloniConfigurationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load moloniConfiguration on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.moloniConfiguration).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
