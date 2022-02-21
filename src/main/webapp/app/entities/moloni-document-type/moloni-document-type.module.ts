import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MoloniDocumentTypeComponent } from './list/moloni-document-type.component';
import { MoloniDocumentTypeDetailComponent } from './detail/moloni-document-type-detail.component';
import { MoloniDocumentTypeUpdateComponent } from './update/moloni-document-type-update.component';
import { MoloniDocumentTypeDeleteDialogComponent } from './delete/moloni-document-type-delete-dialog.component';
import { MoloniDocumentTypeRoutingModule } from './route/moloni-document-type-routing.module';

@NgModule({
  imports: [SharedModule, MoloniDocumentTypeRoutingModule],
  declarations: [
    MoloniDocumentTypeComponent,
    MoloniDocumentTypeDetailComponent,
    MoloniDocumentTypeUpdateComponent,
    MoloniDocumentTypeDeleteDialogComponent,
  ],
  entryComponents: [MoloniDocumentTypeDeleteDialogComponent],
})
export class MoloniDocumentTypeModule {}
