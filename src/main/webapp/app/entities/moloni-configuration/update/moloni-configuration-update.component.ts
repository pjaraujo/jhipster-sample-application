import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IMoloniConfiguration, MoloniConfiguration } from '../moloni-configuration.model';
import { MoloniConfigurationService } from '../service/moloni-configuration.service';

@Component({
  selector: 'jhi-moloni-configuration-update',
  templateUrl: './moloni-configuration-update.component.html',
})
export class MoloniConfigurationUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    username: [],
    password: [],
    company: [],
    client: [],
    secret: [],
  });

  constructor(
    protected moloniConfigurationService: MoloniConfigurationService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ moloniConfiguration }) => {
      this.updateForm(moloniConfiguration);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const moloniConfiguration = this.createFromForm();
    if (moloniConfiguration.id !== undefined) {
      this.subscribeToSaveResponse(this.moloniConfigurationService.update(moloniConfiguration));
    } else {
      this.subscribeToSaveResponse(this.moloniConfigurationService.create(moloniConfiguration));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMoloniConfiguration>>): void {
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

  protected updateForm(moloniConfiguration: IMoloniConfiguration): void {
    this.editForm.patchValue({
      id: moloniConfiguration.id,
      username: moloniConfiguration.username,
      password: moloniConfiguration.password,
      company: moloniConfiguration.company,
      client: moloniConfiguration.client,
      secret: moloniConfiguration.secret,
    });
  }

  protected createFromForm(): IMoloniConfiguration {
    return {
      ...new MoloniConfiguration(),
      id: this.editForm.get(['id'])!.value,
      username: this.editForm.get(['username'])!.value,
      password: this.editForm.get(['password'])!.value,
      company: this.editForm.get(['company'])!.value,
      client: this.editForm.get(['client'])!.value,
      secret: this.editForm.get(['secret'])!.value,
    };
  }
}
