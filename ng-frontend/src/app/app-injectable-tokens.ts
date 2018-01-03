import { InjectionToken } from '@angular/core';
import { ProductRepository } from './domain/product-repository';

export const PRODUCT_REPOSITORY = new InjectionToken<ProductRepository>('ProductRepository');
