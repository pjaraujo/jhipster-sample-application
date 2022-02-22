import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IShippingProcess, ShippingProcess } from '../shipping-process.model';
import { ShippingProcessService } from '../service/shipping-process.service';

@Component({
  selector: 'jhi-shipping-process-update',
  templateUrl: './shipping-process-update.component.html',
})
export class ShippingProcessUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
  });

  constructor(
    protected shippingProcessService: ShippingProcessService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ shippingProcess }) => {
      this.updateForm(shippingProcess);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const shippingProcess = this.createFromForm();
    if (shippingProcess.id !== undefined) {
      this.subscribeToSaveResponse(this.shippingProcessService.update(shippingProcess));
    } else {
      this.subscribeToSaveResponse(this.shippingProcessService.create(shippingProcess));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IShippingProcess>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(shippingProcess: IShippingProcess): void {
    this.editForm.patchValue({
      id: shippingProcess.id,
    });
  }

  protected createFromForm(): IShippingProcess {
    return {
      ...new ShippingProcess(),
      id: this.editForm.get(['id'])!.value,
    };
  }
}
