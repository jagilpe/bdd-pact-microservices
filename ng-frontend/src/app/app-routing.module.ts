import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePage } from './home/home.page';
import { CategoryPage } from './category/category.page';

export const appRoutes: Routes = [
  { path: '', component: HomePage },
  { path: 'category/:categoryId', component: CategoryPage }
];

@NgModule({
  imports: [ RouterModule.forRoot(appRoutes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
