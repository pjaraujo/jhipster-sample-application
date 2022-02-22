import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MoloniExpeditionDocumentComponent } from './list/moloni-expedition-document.component';
import { MoloniExpeditionDocumentDetailComponent } from './detail/moloni-expedition-document-detail.component';
import { MoloniExpeditionDocumentUpdateComponent } from './update/moloni-expedition-document-update.component';
import { MoloniExpeditionDocumentDeleteDialogComponent } from './delete/moloni-expedition-document-delete-dialog.component';
import { MoloniExpeditionDocumentRoutingModule } from './route/moloni-expedition-document-routing.module';

@NgModule({
  imports: [SharedModule, MoloniExpeditionDocumentRoutingModule],
  declarations: [
    MoloniExpeditionDocumentComponent,
    MoloniExpeditionDocumentDetailComponent,
    MoloniExpeditionDocumentUpdateComponent,
    MoloniExpeditionDocumentDeleteDialogComponent,
  ],
  entryComponents: [MoloniExpeditionDocumentDeleteDialogComponent],
})
export class MoloniExpeditionDocumentModule {}
