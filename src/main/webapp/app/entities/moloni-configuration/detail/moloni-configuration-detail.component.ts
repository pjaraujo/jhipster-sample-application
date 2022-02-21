import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMoloniConfiguration } from '../moloni-configuration.model';

@Component({
  selector: 'jhi-moloni-configuration-detail',
  templateUrl: './moloni-configuration-detail.component.html',
})
export class MoloniConfigurationDetailComponent implements OnInit {
  moloniConfiguration: IMoloniConfiguration | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ moloniConfiguration }) => {
      this.moloniConfiguration = moloniConfiguration;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
