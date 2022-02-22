import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMoloniExpeditionDocument } from '../moloni-expedition-document.model';
import { MoloniExpeditionDocumentService } from '../service/moloni-expedition-document.service';

@Component({
  templateUrl: './moloni-expedition-document-delete-dialog.component.html',
})
export class MoloniExpeditionDocumentDeleteDialogComponent {
  moloniExpeditionDocument?: IMoloniExpeditionDocument;

  constructor(protected moloniExpeditionDocumentService: MoloniExpeditionDocumentService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.moloniExpeditionDocumentService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
