import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { HomePage } from './home/home.page';
import { CategoryPage } from './category/category.page';
import { CoreModule } from './core/core.module';
import { ProductOffersPage } from './offers/product-offers.page';


@NgModule({
  declarations: [
    AppComponent,
    HomePage,
    CategoryPage,
    ProductOffersPage
  ],
  imports: [
    AppRoutingModule,
    BrowserModule,
    CoreModule
  ],
  providers: [],
  entryComponents: [
    HomePage,
    CategoryPage,
    ProductOffersPage
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
