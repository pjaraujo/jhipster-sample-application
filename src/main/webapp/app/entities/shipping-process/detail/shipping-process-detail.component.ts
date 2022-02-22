import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IShippingProcess } from '../shipping-process.model';

@Component({
  selector: 'jhi-shipping-process-detail',
  templateUrl: './shipping-process-detail.component.html',
})
export class ShippingProcessDetailComponent implements OnInit {
  shippingProcess: IShippingProcess | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ shippingProcess }) => {
      this.shippingProcess = shippingProcess;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
