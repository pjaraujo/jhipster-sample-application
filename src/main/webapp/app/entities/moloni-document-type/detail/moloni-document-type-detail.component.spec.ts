import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MoloniDocumentTypeDetailComponent } from './moloni-document-type-detail.component';

describe('MoloniDocumentType Management Detail Component', () => {
  let comp: MoloniDocumentTypeDetailComponent;
  let fixture: ComponentFixture<MoloniDocumentTypeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MoloniDocumentTypeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ moloniDocumentType: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(MoloniDocumentTypeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(MoloniDocumentTypeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load moloniDocumentType on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.moloniDocumentType).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
