import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MoloniReceptionDocumentDetailComponent } from './moloni-reception-document-detail.component';

describe('MoloniReceptionDocument Management Detail Component', () => {
  let comp: MoloniReceptionDocumentDetailComponent;
  let fixture: ComponentFixture<MoloniReceptionDocumentDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MoloniReceptionDocumentDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ moloniReceptionDocument: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(MoloniReceptionDocumentDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(MoloniReceptionDocumentDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load moloniReceptionDocument on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.moloniReceptionDocument).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
