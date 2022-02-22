import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ShippingProcessComponent } from '../list/shipping-process.component';
import { ShippingProcessDetailComponent } from '../detail/shipping-process-detail.component';
import { ShippingProcessUpdateComponent } from '../update/shipping-process-update.component';
import { ShippingProcessRoutingResolveService } from './shipping-process-routing-resolve.service';

const shippingProcessRoute: Routes = [
  {
    path: '',
    component: ShippingProcessComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ShippingProcessDetailComponent,
    resolve: {
      shippingProcess: ShippingProcessRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ShippingProcessUpdateComponent,
    resolve: {
      shippingProcess: ShippingProcessRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ShippingProcessUpdateComponent,
    resolve: {
      shippingProcess: ShippingProcessRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(shippingProcessRoute)],
  exports: [RouterModule],
})
export class ShippingProcessRoutingModule {}
