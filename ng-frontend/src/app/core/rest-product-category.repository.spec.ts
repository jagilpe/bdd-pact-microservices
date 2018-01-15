import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { inject, TestBed } from '@angular/core/testing';
import { BACKEND_URL } from '../app.parameters';
import { RestProductCategoryRepository } from './rest-product-category.repository';
import { ProductCategory } from '../domain/product-category';

const chai = require('chai').use(require('chai-as-promised'));
const expect = chai.expect;

describe('The Rest ProductCategoryRepository', () => {

  const categories: ProductCategory[] = [
    { id: 1, name: 'Category 1'},
    { id: 2, name: 'Category 2'},
    { id: 3, name: 'Category 3'},
    { id: 4, name: 'Category 4'},
    { id: 5, name: 'Category 5'}
  ];

  const backendUrl: string = '/api/v1';

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        RestProductCategoryRepository,
        { provide: BACKEND_URL, useValue: backendUrl }
      ],
      imports: [ HttpClientTestingModule]
    });
  });

  it('should return the product categories', inject(
    [RestProductCategoryRepository, HttpTestingController],
    (productCategoryRepository: RestProductCategoryRepository, httpMock: HttpTestingController) => {
      expect(productCategoryRepository.findAll().toPromise()).to.eventually.equal(categories);

      const request = httpMock.expectOne(`${backendUrl}/categories`);
      expect(request.request.method).to.equal('GET');
      request.flush(categories);
      httpMock.verify();
  }));

});
