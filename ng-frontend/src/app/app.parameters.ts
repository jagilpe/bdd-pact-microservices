import { InjectionToken } from '@angular/core';
import { environment } from '../environments/environment';

export const BACKEND_URL = new InjectionToken<string>('BACKEND_URL');

export const injectableParameters = [
  { provide: BACKEND_URL, useValue: environment.apiBackendUrl}
];
