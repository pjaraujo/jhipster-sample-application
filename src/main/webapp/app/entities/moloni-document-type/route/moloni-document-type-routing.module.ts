import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MoloniDocumentTypeComponent } from '../list/moloni-document-type.component';
import { MoloniDocumentTypeDetailComponent } from '../detail/moloni-document-type-detail.component';
import { MoloniDocumentTypeUpdateComponent } from '../update/moloni-document-type-update.component';
import { MoloniDocumentTypeRoutingResolveService } from './moloni-document-type-routing-resolve.service';

const moloniDocumentTypeRoute: Routes = [
  {
    path: '',
    component: MoloniDocumentTypeComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MoloniDocumentTypeDetailComponent,
    resolve: {
      moloniDocumentType: MoloniDocumentTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MoloniDocumentTypeUpdateComponent,
    resolve: {
      moloniDocumentType: MoloniDocumentTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MoloniDocumentTypeUpdateComponent,
    resolve: {
      moloniDocumentType: MoloniDocumentTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(moloniDocumentTypeRoute)],
  exports: [RouterModule],
})
export class MoloniDocumentTypeRoutingModule {}
