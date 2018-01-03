import { NgModule, Optional, SkipSelf } from '@angular/core';
import { RestProductRepository } from './rest-product.repository';
import { PRODUCT_REPOSITORY } from '../app-injectable-tokens';
import { HttpClientModule } from '@angular/common/http';
import { throwIfAlreadyLoaded } from '../module-import-guard';
import { injectableParameters } from '../app.parameters';

@NgModule({
  providers: [
    { provide: PRODUCT_REPOSITORY, useClass: RestProductRepository },
    ...injectableParameters
  ],
  imports: [
    HttpClientModule
  ]
})
export class CoreModule {
  constructor(@Optional() @SkipSelf() parentModule: CoreModule) {
    throwIfAlreadyLoaded(parentModule, 'CoreModule');
  }
}
