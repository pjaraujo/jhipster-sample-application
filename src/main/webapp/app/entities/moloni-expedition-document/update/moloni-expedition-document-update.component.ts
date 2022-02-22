import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IMoloniExpeditionDocument, MoloniExpeditionDocument } from '../moloni-expedition-document.model';
import { MoloniExpeditionDocumentService } from '../service/moloni-expedition-document.service';
import { IShippingProcess } from 'app/entities/shipping-process/shipping-process.model';
import { ShippingProcessService } from 'app/entities/shipping-process/service/shipping-process.service';
import { IMoloniConfiguration } from 'app/entities/moloni-configuration/moloni-configuration.model';
import { MoloniConfigurationService } from 'app/entities/moloni-configuration/service/moloni-configuration.service';
import { MoloniEndpoints } from 'app/entities/enumerations/moloni-endpoints.model';

@Component({
  selector: 'jhi-moloni-expedition-document-update',
  templateUrl: './moloni-expedition-document-update.component.html',
})
export class MoloniExpeditionDocumentUpdateComponent implements OnInit {
  isSaving = false;
  moloniEndpointsValues = Object.keys(MoloniEndpoints);

  shippingProcessesSharedCollection: IShippingProcess[] = [];
  moloniConfigurationsSharedCollection: IMoloniConfiguration[] = [];

  editForm = this.fb.group({
    id: [],
    origin: [],
    destination: [],
    shippingProcess: [],
    config: [],
  });

  constructor(
    protected moloniExpeditionDocumentService: MoloniExpeditionDocumentService,
    protected shippingProcessService: ShippingProcessService,
    protected moloniConfigurationService: MoloniConfigurationService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ moloniExpeditionDocument }) => {
      this.updateForm(moloniExpeditionDocument);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const moloniExpeditionDocument = this.createFromForm();
    if (moloniExpeditionDocument.id !== undefined) {
      this.subscribeToSaveResponse(this.moloniExpeditionDocumentService.update(moloniExpeditionDocument));
    } else {
      this.subscribeToSaveResponse(this.moloniExpeditionDocumentService.create(moloniExpeditionDocument));
    }
  }

  trackShippingProcessById(index: number, item: IShippingProcess): number {
    return item.id!;
  }

  trackMoloniConfigurationById(index: number, item: IMoloniConfiguration): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMoloniExpeditionDocument>>): void {
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

  protected updateForm(moloniExpeditionDocument: IMoloniExpeditionDocument): void {
    this.editForm.patchValue({
      id: moloniExpeditionDocument.id,
      origin: moloniExpeditionDocument.origin,
      destination: moloniExpeditionDocument.destination,
      shippingProcess: moloniExpeditionDocument.shippingProcess,
      config: moloniExpeditionDocument.config,
    });

    this.shippingProcessesSharedCollection = this.shippingProcessService.addShippingProcessToCollectionIfMissing(
      this.shippingProcessesSharedCollection,
      moloniExpeditionDocument.shippingProcess
    );
    this.moloniConfigurationsSharedCollection = this.moloniConfigurationService.addMoloniConfigurationToCollectionIfMissing(
      this.moloniConfigurationsSharedCollection,
      moloniExpeditionDocument.config
    );
  }

  protected loadRelationshipsOptions(): void {
    this.shippingProcessService
      .query()
      .pipe(map((res: HttpResponse<IShippingProcess[]>) => res.body ?? []))
      .pipe(
        map((shippingProcesses: IShippingProcess[]) =>
          this.shippingProcessService.addShippingProcessToCollectionIfMissing(
            shippingProcesses,
            this.editForm.get('shippingProcess')!.value
          )
        )
      )
      .subscribe((shippingProcesses: IShippingProcess[]) => (this.shippingProcessesSharedCollection = shippingProcesses));

    this.moloniConfigurationService
      .query()
      .pipe(map((res: HttpResponse<IMoloniConfiguration[]>) => res.body ?? []))
      .pipe(
        map((moloniConfigurations: IMoloniConfiguration[]) =>
          this.moloniConfigurationService.addMoloniConfigurationToCollectionIfMissing(
            moloniConfigurations,
            this.editForm.get('config')!.value
          )
        )
      )
      .subscribe((moloniConfigurations: IMoloniConfiguration[]) => (this.moloniConfigurationsSharedCollection = moloniConfigurations));
  }

  protected createFromForm(): IMoloniExpeditionDocument {
    return {
      ...new MoloniExpeditionDocument(),
      id: this.editForm.get(['id'])!.value,
      origin: this.editForm.get(['origin'])!.value,
      destination: this.editForm.get(['destination'])!.value,
      shippingProcess: this.editForm.get(['shippingProcess'])!.value,
      config: this.editForm.get(['config'])!.value,
    };
  }
}
