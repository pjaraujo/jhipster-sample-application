import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMoloniConfiguration } from '../moloni-configuration.model';
import { MoloniConfigurationService } from '../service/moloni-configuration.service';

@Component({
  templateUrl: './moloni-configuration-delete-dialog.component.html',
})
export class MoloniConfigurationDeleteDialogComponent {
  moloniConfiguration?: IMoloniConfiguration;

  constructor(protected moloniConfigurationService: MoloniConfigurationService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.moloniConfigurationService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
