import { NgModule, Optional, SkipSelf } from '@angular/core';
import { RestProductRepository } from './rest-product.repository';
import { PRODUCT_OFFER_REPOSITORY, PRODUCT_REPOSITORY } from '../app-injectable-tokens';
import { HttpClientModule } from '@angular/common/http';
import { throwIfAlreadyLoaded } from '../module-import-guard';
import { injectableParameters } from '../app.parameters';
import { RestProductOfferRepository } from './rest-product-offer.repository';

@NgModule({
  providers: [
    { provide: PRODUCT_REPOSITORY, useClass: RestProductRepository },
    { provide: PRODUCT_OFFER_REPOSITORY, useClass: RestProductOfferRepository },
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
