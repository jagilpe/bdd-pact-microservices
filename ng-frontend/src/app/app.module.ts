import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { HomePage } from './home/home.page';


@NgModule({
  declarations: [
    AppComponent,
    HomePage
  ],
  imports: [
    AppRoutingModule,
    BrowserModule
  ],
  providers: [],
  entryComponents: [
    HomePage
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
