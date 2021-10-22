import {
  async,
  ComponentFixture,
  fakeAsync,
  TestBed,
} from '@angular/core/testing';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';
import { Product } from '../shared/models/product';
import { ProductService } from '../shared/services/product.service';

import { ProductListComponent } from './product-list.component';

describe('ProductListComponent', () => {
  let component: ProductListComponent;
  let fixture: ComponentFixture<ProductListComponent>;

  let productService: ProductService;
  let productServiceMock = jasmine.createSpyObj('ProductService', ['getAll']);
  let router: Router;

  const products: Product[] = [
    {
      id: 1,
      sku: 'FAL-10000',
      name: 'producto',
      brand: 'marca',
      price: 300,
      images: [{ id: 1, url: 'img.png', type: 'MAIN' }],
    },
  ];

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ProductListComponent],
      imports: [RouterTestingModule.withRoutes([])],
      providers: [{ provide: ProductService, useValue: productServiceMock }],
    }).compileComponents();

    productServiceMock.getAll.and.returnValue(of(products));
    productService = TestBed.inject(ProductService);
    router = TestBed.inject(Router);
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('tests getAll', () => {
    it('should return all products', fakeAsync(() => {
      component.getProducts();

      expect(component.products).toEqual(products);
    }));
  });

  describe('tests goToDetail', () => {
    it('should return all products', fakeAsync(() => {
      const navigateSpy = spyOn(router, 'navigate');

      component.goToDetail('FAL-1000');
      expect(navigateSpy).toHaveBeenCalledWith(['/product-details/FAL-1000']);
    }));
  });
});
