import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMoloniReceptionDocument } from '../moloni-reception-document.model';
import { MoloniReceptionDocumentService } from '../service/moloni-reception-document.service';

@Component({
  templateUrl: './moloni-reception-document-delete-dialog.component.html',
})
export class MoloniReceptionDocumentDeleteDialogComponent {
  moloniReceptionDocument?: IMoloniReceptionDocument;

  constructor(protected moloniReceptionDocumentService: MoloniReceptionDocumentService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.moloniReceptionDocumentService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
