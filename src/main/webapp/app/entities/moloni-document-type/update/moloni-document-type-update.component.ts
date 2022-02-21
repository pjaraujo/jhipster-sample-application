import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IMoloniDocumentType, MoloniDocumentType } from '../moloni-document-type.model';
import { MoloniDocumentTypeService } from '../service/moloni-document-type.service';
import { IMoloniConfiguration } from 'app/entities/moloni-configuration/moloni-configuration.model';
import { MoloniConfigurationService } from 'app/entities/moloni-configuration/service/moloni-configuration.service';
import { DocumentType } from 'app/entities/enumerations/document-type.model';

@Component({
  selector: 'jhi-moloni-document-type-update',
  templateUrl: './moloni-document-type-update.component.html',
})
export class MoloniDocumentTypeUpdateComponent implements OnInit {
  isSaving = false;
  documentTypeValues = Object.keys(DocumentType);

  moloniConfigurationsSharedCollection: IMoloniConfiguration[] = [];

  editForm = this.fb.group({
    id: [],
    documentType: [],
    config1: [],
    config2: [],
    config3: [],
    config4: [],
  });

  constructor(
    protected moloniDocumentTypeService: MoloniDocumentTypeService,
    protected moloniConfigurationService: MoloniConfigurationService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ moloniDocumentType }) => {
      this.updateForm(moloniDocumentType);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const moloniDocumentType = this.createFromForm();
    if (moloniDocumentType.id !== undefined) {
      this.subscribeToSaveResponse(this.moloniDocumentTypeService.update(moloniDocumentType));
    } else {
      this.subscribeToSaveResponse(this.moloniDocumentTypeService.create(moloniDocumentType));
    }
  }

  trackMoloniConfigurationById(index: number, item: IMoloniConfiguration): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMoloniDocumentType>>): void {
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

  protected updateForm(moloniDocumentType: IMoloniDocumentType): void {
    this.editForm.patchValue({
      id: moloniDocumentType.id,
      documentType: moloniDocumentType.documentType,
      config1: moloniDocumentType.config1,
      config2: moloniDocumentType.config2,
      config3: moloniDocumentType.config3,
      config4: moloniDocumentType.config4,
    });

    this.moloniConfigurationsSharedCollection = this.moloniConfigurationService.addMoloniConfigurationToCollectionIfMissing(
      this.moloniConfigurationsSharedCollection,
      moloniDocumentType.config1,
      moloniDocumentType.config2,
      moloniDocumentType.config3,
      moloniDocumentType.config4
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
            this.editForm.get('config1')!.value,
            this.editForm.get('config2')!.value,
            this.editForm.get('config3')!.value,
            this.editForm.get('config4')!.value
          )
        )
      )
      .subscribe((moloniConfigurations: IMoloniConfiguration[]) => (this.moloniConfigurationsSharedCollection = moloniConfigurations));
  }

  protected createFromForm(): IMoloniDocumentType {
    return {
      ...new MoloniDocumentType(),
      id: this.editForm.get(['id'])!.value,
      documentType: this.editForm.get(['documentType'])!.value,
      config1: this.editForm.get(['config1'])!.value,
      config2: this.editForm.get(['config2'])!.value,
      config3: this.editForm.get(['config3'])!.value,
      config4: this.editForm.get(['config4'])!.value,
    };
  }
}
