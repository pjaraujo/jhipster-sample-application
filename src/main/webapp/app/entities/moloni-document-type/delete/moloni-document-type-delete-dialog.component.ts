import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMoloniDocumentType } from '../moloni-document-type.model';
import { MoloniDocumentTypeService } from '../service/moloni-document-type.service';

@Component({
  templateUrl: './moloni-document-type-delete-dialog.component.html',
})
export class MoloniDocumentTypeDeleteDialogComponent {
  moloniDocumentType?: IMoloniDocumentType;

  constructor(protected moloniDocumentTypeService: MoloniDocumentTypeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.moloniDocumentTypeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
