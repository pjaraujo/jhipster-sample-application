import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IShippingProcess } from '../shipping-process.model';
import { ShippingProcessService } from '../service/shipping-process.service';

@Component({
  templateUrl: './shipping-process-delete-dialog.component.html',
})
export class ShippingProcessDeleteDialogComponent {
  shippingProcess?: IShippingProcess;

  constructor(protected shippingProcessService: ShippingProcessService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.shippingProcessService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
