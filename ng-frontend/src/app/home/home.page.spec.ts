import { async, TestBed } from '@angular/core/testing';
import { HomePage } from './home.page';
import { PRODUCT_CATEGORY_REPOSITORY } from '../app-injectable-tokens';
import { ProductCategoryRepository } from '../domain/product-category-repository';
import { Observable } from 'rxjs/Observable';
import { ProductCategory } from '../domain/product-category';
import { By } from '@angular/platform-browser';
import * as _ from 'underscore';
import createSpyObj = jasmine.createSpyObj;
import Spy = jasmine.Spy;

describe('HomePage', () => {

  const categories: Array<ProductCategory> = [
    { id: 1, name: 'Category 1' },
    { id: 2, name: 'Category 2' },
    { id: 3, name: 'Category 3' },
    { id: 4, name: 'Category 4' }
  ];
  let productCategoryRepository: ProductCategoryRepository;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        HomePage
      ],
      providers: [
        { provide: PRODUCT_CATEGORY_REPOSITORY, useFactory: () => createSpyObj('ProductRepository', ['findAll']) }
      ]
    }).compileComponents();

    productCategoryRepository = TestBed.get(PRODUCT_CATEGORY_REPOSITORY);
    (<Spy> productCategoryRepository.findAll).and.returnValue(Observable.of(categories));
  }));

  it('should create the page', async(() => {
    const fixture = TestBed.createComponent(HomePage);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));

  it('should render title in a h1 tag', async(() => {
    const fixture = TestBed.createComponent(HomePage);
    fixture.detectChanges();
    const compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('h1').textContent).toContain('Welcome to My Product Catalogue!');
  }));

  it('should show the list of categories', (done) => {

    const fixture = TestBed.createComponent(HomePage);
    fixture.detectChanges();
    fixture.whenStable()
      .then(() => {
        const categoryElems = fixture.debugElement.queryAll(By.css('#category-list .category'));
        expect(categoryElems.length).toBe(categories.length);
        _.forEach(categoryElems, (categoryElem, index) => expect(categoryElem.nativeElement.innerText).toBe(categories[index].name));

        done();
      });
  });
});
