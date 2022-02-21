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
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
