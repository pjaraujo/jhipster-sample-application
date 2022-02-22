import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IMoloniReceptionDocument, MoloniReceptionDocument } from '../moloni-reception-document.model';
import { MoloniReceptionDocumentService } from '../service/moloni-reception-document.service';
import { IMoloniConfiguration } from 'app/entities/moloni-configuration/moloni-configuration.model';
import { MoloniConfigurationService } from 'app/entities/moloni-configuration/service/moloni-configuration.service';
import { MoloniEndpoints } from 'app/entities/enumerations/moloni-endpoints.model';
import { DocumentType } from 'app/entities/enumerations/document-type.model';

@Component({
  selector: 'jhi-moloni-reception-document-update',
  templateUrl: './moloni-reception-document-update.component.html',
})
export class MoloniReceptionDocumentUpdateComponent implements OnInit {
  isSaving = false;
  moloniEndpointsValues = Object.keys(MoloniEndpoints);
  documentTypeValues = Object.keys(DocumentType);

  moloniConfigurationsSharedCollection: IMoloniConfiguration[] = [];

  editForm = this.fb.group({
    id: [],
    origin: [],
    destination: [],
    documentType: [],
    config: [],
  });

  constructor(
    protected moloniReceptionDocumentService: MoloniReceptionDocumentService,
    protected moloniConfigurationService: MoloniConfigurationService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ moloniReceptionDocument }) => {
      this.updateForm(moloniReceptionDocument);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const moloniReceptionDocument = this.createFromForm();
    if (moloniReceptionDocument.id !== undefined) {
      this.subscribeToSaveResponse(this.moloniReceptionDocumentService.update(moloniReceptionDocument));
    } else {
      this.subscribeToSaveResponse(this.moloniReceptionDocumentService.create(moloniReceptionDocument));
    }
  }

  trackMoloniConfigurationById(index: number, item: IMoloniConfiguration): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMoloniReceptionDocument>>): void {
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

  protected updateForm(moloniReceptionDocument: IMoloniReceptionDocument): void {
    this.editForm.patchValue({
      id: moloniReceptionDocument.id,
      origin: moloniReceptionDocument.origin,
      destination: moloniReceptionDocument.destination,
      documentType: moloniReceptionDocument.documentType,
      config: moloniReceptionDocument.config,
    });

    this.moloniConfigurationsSharedCollection = this.moloniConfigurationService.addMoloniConfigurationToCollectionIfMissing(
      this.moloniConfigurationsSharedCollection,
      moloniReceptionDocument.config
    );
  }

  protected loadRelationshipsOptions(): void {
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

  protected createFromForm(): IMoloniReceptionDocument {
    return {
      ...new MoloniReceptionDocument(),
      id: this.editForm.get(['id'])!.value,
      origin: this.editForm.get(['origin'])!.value,
      destination: this.editForm.get(['destination'])!.value,
      documentType: this.editForm.get(['documentType'])!.value,
      config: this.editForm.get(['config'])!.value,
    };
  }
}
