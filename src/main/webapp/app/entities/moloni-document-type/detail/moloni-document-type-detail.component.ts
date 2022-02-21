import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMoloniDocumentType } from '../moloni-document-type.model';

@Component({
  selector: 'jhi-moloni-document-type-detail',
  templateUrl: './moloni-document-type-detail.component.html',
})
export class MoloniDocumentTypeDetailComponent implements OnInit {
  moloniDocumentType: IMoloniDocumentType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ moloniDocumentType }) => {
      this.moloniDocumentType = moloniDocumentType;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
