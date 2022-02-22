import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ShippingProcessComponent } from './list/shipping-process.component';
import { ShippingProcessDetailComponent } from './detail/shipping-process-detail.component';
import { ShippingProcessUpdateComponent } from './update/shipping-process-update.component';
import { ShippingProcessDeleteDialogComponent } from './delete/shipping-process-delete-dialog.component';
import { ShippingProcessRoutingModule } from './route/shipping-process-routing.module';

@NgModule({
  imports: [SharedModule, ShippingProcessRoutingModule],
  declarations: [
    ShippingProcessComponent,
    ShippingProcessDetailComponent,
    ShippingProcessUpdateComponent,
    ShippingProcessDeleteDialogComponent,
  ],
  entryComponents: [ShippingProcessDeleteDialogComponent],
})
export class ShippingProcessModule {}
