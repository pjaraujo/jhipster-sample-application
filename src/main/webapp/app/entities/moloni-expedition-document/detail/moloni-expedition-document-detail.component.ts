import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMoloniExpeditionDocument } from '../moloni-expedition-document.model';

@Component({
  selector: 'jhi-moloni-expedition-document-detail',
  templateUrl: './moloni-expedition-document-detail.component.html',
})
export class MoloniExpeditionDocumentDetailComponent implements OnInit {
  moloniExpeditionDocument: IMoloniExpeditionDocument | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ moloniExpeditionDocument }) => {
      this.moloniExpeditionDocument = moloniExpeditionDocument;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
