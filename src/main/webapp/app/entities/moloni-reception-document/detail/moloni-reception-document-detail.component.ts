import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMoloniReceptionDocument } from '../moloni-reception-document.model';

@Component({
  selector: 'jhi-moloni-reception-document-detail',
  templateUrl: './moloni-reception-document-detail.component.html',
})
export class MoloniReceptionDocumentDetailComponent implements OnInit {
  moloniReceptionDocument: IMoloniReceptionDocument | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ moloniReceptionDocument }) => {
      this.moloniReceptionDocument = moloniReceptionDocument;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
