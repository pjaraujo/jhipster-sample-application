import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MoloniReceptionDocumentComponent } from '../list/moloni-reception-document.component';
import { MoloniReceptionDocumentDetailComponent } from '../detail/moloni-reception-document-detail.component';
import { MoloniReceptionDocumentUpdateComponent } from '../update/moloni-reception-document-update.component';
import { MoloniReceptionDocumentRoutingResolveService } from './moloni-reception-document-routing-resolve.service';

const moloniReceptionDocumentRoute: Routes = [
  {
    path: '',
    component: MoloniReceptionDocumentComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MoloniReceptionDocumentDetailComponent,
    resolve: {
      moloniReceptionDocument: MoloniReceptionDocumentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MoloniReceptionDocumentUpdateComponent,
    resolve: {
      moloniReceptionDocument: MoloniReceptionDocumentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MoloniReceptionDocumentUpdateComponent,
    resolve: {
      moloniReceptionDocument: MoloniReceptionDocumentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(moloniReceptionDocumentRoute)],
  exports: [RouterModule],
})
export class MoloniReceptionDocumentRoutingModule {}
