import { inject, TestBed } from '@angular/core/testing';
import { RestProductRepository } from './rest-product.repository';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { BACKEND_URL } from '../app.parameters';
import { Product } from '../domain/product';

const chai = require('chai').use(require('chai-as-promised'));
const expect = chai.expect;

describe('The Product Repository', () => {

  const productId: number = 1;
  const categoryId: number = 1;
  const backendUrl: string = '/api/v1';
  const product: Product = {
    id: productId,
    name: 'Product name',
    manufacturer: 'Manufacturer'
  };
  const products: Product[] = [
    {id: 1, name: 'Product 1', manufacturer: 'Manufacturer 1'},
    {id: 2, name: 'Product 2', manufacturer: 'Manufacturer 2'},
    {id: 3, name: 'Product 3', manufacturer: 'Manufacturer 3'}
  ];

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        RestProductRepository,
        { provide: BACKEND_URL, useValue: backendUrl }
      ],
      imports: [ HttpClientTestingModule]
    });
  });

  it('Should return the products of a category', inject(
    [ RestProductRepository, HttpTestingController ],
    (productRepository: RestProductRepository, httpMock: HttpTestingController) => {

      expect(productRepository.findAllByCategory(categoryId).toPromise()).to.eventually.equal(products);

      const request = httpMock.expectOne(`${backendUrl}/products?category=${categoryId}`);
      expect(request.request.method).to.equal('GET');
      request.flush(products);
      httpMock.verify();
  }));

  it('should return the details of a product', inject(
    [ RestProductRepository, HttpTestingController ],
    (productRepository: RestProductRepository, httpMock: HttpTestingController) => {
      expect(productRepository.findOneById(productId).toPromise()).to.eventually.equal(product);

      const request = httpMock.expectOne(`${backendUrl}/products/${productId}`);
      expect(request.request.method).to.equal('GET');

      request.flush(product);

      httpMock.verify();
    }));
});
