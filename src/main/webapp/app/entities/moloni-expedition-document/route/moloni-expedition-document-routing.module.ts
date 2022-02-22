import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MoloniExpeditionDocumentComponent } from '../list/moloni-expedition-document.component';
import { MoloniExpeditionDocumentDetailComponent } from '../detail/moloni-expedition-document-detail.component';
import { MoloniExpeditionDocumentUpdateComponent } from '../update/moloni-expedition-document-update.component';
import { MoloniExpeditionDocumentRoutingResolveService } from './moloni-expedition-document-routing-resolve.service';

const moloniExpeditionDocumentRoute: Routes = [
  {
    path: '',
    component: MoloniExpeditionDocumentComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MoloniExpeditionDocumentDetailComponent,
    resolve: {
      moloniExpeditionDocument: MoloniExpeditionDocumentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MoloniExpeditionDocumentUpdateComponent,
    resolve: {
      moloniExpeditionDocument: MoloniExpeditionDocumentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MoloniExpeditionDocumentUpdateComponent,
    resolve: {
      moloniExpeditionDocument: MoloniExpeditionDocumentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(moloniExpeditionDocumentRoute)],
  exports: [RouterModule],
})
export class MoloniExpeditionDocumentRoutingModule {}
