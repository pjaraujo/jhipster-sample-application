import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MoloniConfigurationComponent } from './list/moloni-configuration.component';
import { MoloniConfigurationDetailComponent } from './detail/moloni-configuration-detail.component';
import { MoloniConfigurationUpdateComponent } from './update/moloni-configuration-update.component';
import { MoloniConfigurationDeleteDialogComponent } from './delete/moloni-configuration-delete-dialog.component';
import { MoloniConfigurationRoutingModule } from './route/moloni-configuration-routing.module';

@NgModule({
  imports: [SharedModule, MoloniConfigurationRoutingModule],
  declarations: [
    MoloniConfigurationComponent,
    MoloniConfigurationDetailComponent,
    MoloniConfigurationUpdateComponent,
    MoloniConfigurationDeleteDialogComponent,
  ],
  entryComponents: [MoloniConfigurationDeleteDialogComponent],
})
export class MoloniConfigurationModule {}
