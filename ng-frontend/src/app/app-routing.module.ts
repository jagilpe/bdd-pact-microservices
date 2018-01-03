import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePage } from './home/home.page';
import { CategoryPage } from './category/category.page';
import { ProductOffersPage } from './offers/product-offers.page';

export const appRoutes: Routes = [
  { path: '', component: HomePage },
  { path: 'category/:categoryId', component: CategoryPage },
  { path: 'product-offers/:productId', component: ProductOffersPage }
];

@NgModule({
  imports: [ RouterModule.forRoot(appRoutes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
