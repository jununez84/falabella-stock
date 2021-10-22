import { CommonModule } from '@angular/common';
import {
  async,
  ComponentFixture,
  fakeAsync,
  TestBed,
} from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';
import { MultiImageInputComponent } from '../multi-image-input/multi-image-input.component';
import { Product } from '../shared/models/product';
import { ProductService } from '../shared/services/product.service';

import { ProductDetailsComponent } from './product-details.component';

describe('ProductDetailsComponent', () => {
  let component: ProductDetailsComponent;
  let fixture: ComponentFixture<ProductDetailsComponent>;

  let productService: ProductService;
  let productServiceMock = jasmine.createSpyObj('ProductService', [
    'getById',
    'save',
  ]);
  let router: Router;
  let routeFake = {
    snapshot: {
      paramMap: { get: () => 0 },
    },
  };

  const product: Product = {
    id: 1,
    sku: 'FAL-10000',
    name: 'producto',
    brand: 'marca',
    price: 300,
    images: [{ id: 1, url: 'img.png', type: 'MAIN' }],
  };

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ProductDetailsComponent, MultiImageInputComponent],
      imports: [CommonModule, RouterTestingModule.withRoutes([]), FormsModule],
      providers: [
        { provide: ProductService, useValue: productServiceMock },
        { provide: ActivatedRoute, useValue: routeFake },
      ],
    }).compileComponents();

    router = TestBed.inject(Router);
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should redirect to products when cancel', () => {
    const navigateSpy = spyOn(router, 'navigate');

    component.cancel();
    expect(navigateSpy).toHaveBeenCalledWith(['/products']);
  });

  describe('tests for getProduct', () => {
    it('should return a product', fakeAsync(() => {
      productServiceMock.getById.and.returnValue(of(product));

      component.getProduct('FAL-1000');
      expect(component.product).toEqual(product);
    }));
  });

  describe('tests for save', () => {
    it('should save a product', fakeAsync(() => {
      const navigateSpy = spyOn(router, 'navigate');
      productServiceMock.save.and.returnValue(of(product));

      component.product = { ...product };
      component.save();
      expect(component.product).toEqual(product);
      expect(navigateSpy).toHaveBeenCalledWith(['/products']);
    }));
  });
});
