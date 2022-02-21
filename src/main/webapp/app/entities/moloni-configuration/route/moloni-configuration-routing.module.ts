import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MoloniConfigurationComponent } from '../list/moloni-configuration.component';
import { MoloniConfigurationDetailComponent } from '../detail/moloni-configuration-detail.component';
import { MoloniConfigurationUpdateComponent } from '../update/moloni-configuration-update.component';
import { MoloniConfigurationRoutingResolveService } from './moloni-configuration-routing-resolve.service';

const moloniConfigurationRoute: Routes = [
  {
    path: '',
    component: MoloniConfigurationComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MoloniConfigurationDetailComponent,
    resolve: {
      moloniConfiguration: MoloniConfigurationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MoloniConfigurationUpdateComponent,
    resolve: {
      moloniConfiguration: MoloniConfigurationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MoloniConfigurationUpdateComponent,
    resolve: {
      moloniConfiguration: MoloniConfigurationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(moloniConfigurationRoute)],
  exports: [RouterModule],
})
export class MoloniConfigurationRoutingModule {}
