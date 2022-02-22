import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'moloni-configuration',
        data: { pageTitle: 'jhipsterSampleApplicationApp.moloniConfiguration.home.title' },
        loadChildren: () => import('./moloni-configuration/moloni-configuration.module').then(m => m.MoloniConfigurationModule),
      },
      {
        path: 'moloni-document-type',
        data: { pageTitle: 'jhipsterSampleApplicationApp.moloniDocumentType.home.title' },
        loadChildren: () => import('./moloni-document-type/moloni-document-type.module').then(m => m.MoloniDocumentTypeModule),
      },
      {
        path: 'moloni-expedition-document',
        data: { pageTitle: 'jhipsterSampleApplicationApp.moloniExpeditionDocument.home.title' },
        loadChildren: () =>
          import('./moloni-expedition-document/moloni-expedition-document.module').then(m => m.MoloniExpeditionDocumentModule),
      },
      {
        path: 'moloni-reception-document',
        data: { pageTitle: 'jhipsterSampleApplicationApp.moloniReceptionDocument.home.title' },
        loadChildren: () =>
          import('./moloni-reception-document/moloni-reception-document.module').then(m => m.MoloniReceptionDocumentModule),
      },
      {
        path: 'shipping-process',
        data: { pageTitle: 'jhipsterSampleApplicationApp.shippingProcess.home.title' },
        loadChildren: () => import('./shipping-process/shipping-process.module').then(m => m.ShippingProcessModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
