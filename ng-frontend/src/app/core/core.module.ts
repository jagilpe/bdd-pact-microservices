import { NgModule, Optional, SkipSelf } from '@angular/core';
import { RestProductRepository } from './rest-product.repository';
import { PRODUCT_CATEGORY_REPOSITORY, PRODUCT_OFFER_REPOSITORY, PRODUCT_REPOSITORY } from '../app-injectable-tokens';
import { HttpClientModule } from '@angular/common/http';
import { throwIfAlreadyLoaded } from '../module-import-guard';
import { injectableParameters } from '../app.parameters';
import { RestProductOfferRepository } from './rest-product-offer.repository';
import { RestProductCategoryRepository } from './rest-product-category.repository';

@NgModule({
  providers: [
    { provide: PRODUCT_REPOSITORY, useClass: RestProductRepository },
    { provide: PRODUCT_OFFER_REPOSITORY, useClass: RestProductOfferRepository },
    { provide: PRODUCT_CATEGORY_REPOSITORY, useClass: RestProductCategoryRepository },
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
