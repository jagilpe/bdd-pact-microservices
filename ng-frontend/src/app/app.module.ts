import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { HomePage } from './home/home.page';
import { CategoryPage } from './category/category.page';
import { HttpClientModule } from '@angular/common/http';
import { injectableParameters } from './app.parameters';


@NgModule({
  declarations: [
    AppComponent,
    HomePage,
    CategoryPage
  ],
  imports: [
    AppRoutingModule,
    BrowserModule,
    HttpClientModule
  ],
  providers: [
    ...injectableParameters
  ],
  entryComponents: [
    HomePage,
    CategoryPage
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
