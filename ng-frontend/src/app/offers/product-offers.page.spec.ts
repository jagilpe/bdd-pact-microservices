import { async, TestBed } from '@angular/core/testing';
import { PRODUCT_REPOSITORY } from '../app-injectable-tokens';
import { Observable } from 'rxjs/Observable';
import { ActivatedRoute, convertToParamMap, ParamMap } from '@angular/router';
import { ProductOffersPage } from './product-offers.page';
import { By } from '@angular/platform-browser';
import { Product } from '../domain/product';
import { ProductRepository } from '../domain/product-repository';
import createSpyObj = jasmine.createSpyObj;
import Spy = jasmine.Spy;

describe('The Product Offers page', () => {

  const productId: number = 1;
  const paramMap: ParamMap = convertToParamMap({ productId: productId });
  const product: Product = {
    id: 1,
    name: 'Product 1',
    manufacturer: 'Manufacturer 1'
  };

  let productRepository: ProductRepository;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        ProductOffersPage
      ],
      providers: [
        { provide: PRODUCT_REPOSITORY, useFactory: () => createSpyObj('ProductRepository', ['findOneById']) },
        { provide: ActivatedRoute, useValue: { paramMap: Observable.of(paramMap) } }
      ]
    }).compileComponents();

    productRepository = TestBed.get(PRODUCT_REPOSITORY);
    (<Spy> productRepository.findOneById).and.returnValue(Observable.of(product));
  }));

  it('should be created', async(() => {
    const fixture = TestBed.createComponent(ProductOffersPage);
    const productOffersPage = fixture.debugElement.componentInstance;
    expect(productOffersPage).toBeTruthy();
  }));

  it('should show the details of a product', (done)  => {
    const fixture = TestBed.createComponent(ProductOffersPage);
    fixture.detectChanges();
    fixture.whenStable()
      .then(() => {
        const productName = fixture.debugElement.query(By.css('#product-name'));
        const productManufacturer = fixture.debugElement.query(By.css('#product-manufacturer'));
        expect(productName.nativeElement.innerText).toBe(product.name);
        expect(productManufacturer.nativeElement.innerText).toBe(product.manufacturer);

        expect(productRepository.findOneById).toHaveBeenCalledWith(productId);
        done();
      });
  });
});
