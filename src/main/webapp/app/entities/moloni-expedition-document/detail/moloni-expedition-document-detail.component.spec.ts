import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MoloniExpeditionDocumentDetailComponent } from './moloni-expedition-document-detail.component';

describe('MoloniExpeditionDocument Management Detail Component', () => {
  let comp: MoloniExpeditionDocumentDetailComponent;
  let fixture: ComponentFixture<MoloniExpeditionDocumentDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MoloniExpeditionDocumentDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ moloniExpeditionDocument: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(MoloniExpeditionDocumentDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(MoloniExpeditionDocumentDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load moloniExpeditionDocument on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.moloniExpeditionDocument).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
