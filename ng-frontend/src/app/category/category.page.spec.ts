import { async, TestBed } from '@angular/core/testing';
import { CategoryPage } from './category.page';
import { Product } from '../domain/product';
import { By } from '@angular/platform-browser';
import * as _ from 'underscore';
import { ActivatedRoute, convertToParamMap, ParamMap } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import { PRODUCT_REPOSITORY } from '../app-injectable-tokens';
import { ProductRepository } from '../domain/product-repository';
import { RouterTestingModule } from '@angular/router/testing';
import createSpyObj = jasmine.createSpyObj;
import Spy = jasmine.Spy;

describe('The Category Page', () => {

  const categoryId: number = 1;
  const paramMap: ParamMap = convertToParamMap({ categoryId: categoryId });
  let productRepository: ProductRepository;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        CategoryPage
      ],
      providers: [
        { provide: PRODUCT_REPOSITORY, useFactory: () => createSpyObj('ProductRepository', ['findAllByCategory']) },
        { provide: ActivatedRoute, useValue: { paramMap: Observable.of(paramMap) } }
      ],
      imports: [
        RouterTestingModule
      ]
    }).compileComponents();

    productRepository = TestBed.get(PRODUCT_REPOSITORY);
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

    (<Spy> productRepository.findAllByCategory).and.returnValue(Observable.of(products));

    const fixture = TestBed.createComponent(CategoryPage);
    fixture.detectChanges();
    fixture.whenStable()
      .then(() => {
        const productElems = fixture.debugElement.queryAll(By.css('#product-list .product'));
        expect(productElems.length).toBe(products.length);
        _.forEach(productElems, (productElem, index) => expect(productElem.nativeElement.innerText).toBe(products[index].name));

        expect(productRepository.findAllByCategory).toHaveBeenCalledWith(categoryId);
        done();
      });
  });
});
