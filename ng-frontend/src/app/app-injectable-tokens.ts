import { InjectionToken } from '@angular/core';
import { ProductRepository } from './domain/product-repository';
import { ProductOfferRepository } from './domain/product-offer-repository';

export const PRODUCT_REPOSITORY = new InjectionToken<ProductRepository>('ProductRepository');
export const PRODUCT_OFFER_REPOSITORY = new InjectionToken<ProductOfferRepository>('ProductOfferRepository');
