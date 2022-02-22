import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MoloniReceptionDocumentComponent } from './list/moloni-reception-document.component';
import { MoloniReceptionDocumentDetailComponent } from './detail/moloni-reception-document-detail.component';
import { MoloniReceptionDocumentUpdateComponent } from './update/moloni-reception-document-update.component';
import { MoloniReceptionDocumentDeleteDialogComponent } from './delete/moloni-reception-document-delete-dialog.component';
import { MoloniReceptionDocumentRoutingModule } from './route/moloni-reception-document-routing.module';

@NgModule({
  imports: [SharedModule, MoloniReceptionDocumentRoutingModule],
  declarations: [
    MoloniReceptionDocumentComponent,
    MoloniReceptionDocumentDetailComponent,
    MoloniReceptionDocumentUpdateComponent,
    MoloniReceptionDocumentDeleteDialogComponent,
  ],
  entryComponents: [MoloniReceptionDocumentDeleteDialogComponent],
})
export class MoloniReceptionDocumentModule {}
