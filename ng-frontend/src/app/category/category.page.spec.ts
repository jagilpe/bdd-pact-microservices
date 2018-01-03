import { async, TestBed } from '@angular/core/testing';
import { CategoryPage } from './category.page';
import { Product } from '../domain/product';
import { By } from '@angular/platform-browser';
import * as _ from 'underscore';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { BACKEND_URL } from '../app.parameters';
import { ActivatedRoute, convertToParamMap, ParamMap } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';

describe('The Category Page', () => {

  const categoryId: number = 1;
  const paramMap: ParamMap = convertToParamMap({ categoryId: categoryId });
  const backendUrl: string = '/api';
  let httpMock: HttpTestingController;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        CategoryPage
      ],
      providers: [
        { provide: BACKEND_URL, useValue: backendUrl },
        { provide: ActivatedRoute, useValue: { paramMap: Observable.of(paramMap) } }
      ],
      imports: [ HttpClientTestingModule ]
    }).compileComponents();

    httpMock = TestBed.get(HttpTestingController);
  }));

  it('should be built', async(() => {
    const fixture = TestBed.createComponent(CategoryPage);
    const component = fixture.debugElement.componentInstance;
    expect(component).toBeTruthy();
  }));

  it('should show the list of products of a category', (done) => {
    const products: Array<Product> = [
      { id: 1, name: 'Product 1', manufacturer: 'Manufacturer 1'},
      { id: 2, name: 'Product 2', manufacturer: 'Manufacturer 2'},
      { id: 3, name: 'Product 3', manufacturer: 'Manufacturer 3'},
      { id: 4, name: 'Product 4', manufacturer: 'Manufacturer 1'}
    ];

    const fixture = TestBed.createComponent(CategoryPage);
    fixture.detectChanges();

    const request = httpMock.expectOne(`${backendUrl}/products?category=${categoryId}`);
    expect(request.request.method).toEqual('GET');
    request.flush(products);

    fixture.whenStable()
      .then(() => {
        fixture.detectChanges();
        return fixture.whenStable();
      })
      .then(() => {
        const productElems = fixture.debugElement.queryAll(By.css('#product-list .product'));
        expect(productElems.length).toBe(products.length);
        _.forEach(productElems, (productElem, index) => expect(productElem.nativeElement.innerText).toBe(products[index].name));

        httpMock.verify();
        done();
      });
  });
});
