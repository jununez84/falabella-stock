import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { Product } from '../shared/models/product';
import { ProductService } from '../shared/services/product.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.scss'],
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];

  constructor(private router: Router, private productService: ProductService) {}

  ngOnInit(): void {
    this.getProducts();
  }

  getProducts() {
    this.productService.getAll().subscribe((products) => {
      this.products = products.map((p) => {
        return {
          ...p,
          images: [p.images.find((i) => i.type === 'MAIN')],
        };
      });
    });
  }

  goToDetail(sku: string | void) {
    const path = `/product-details/${sku}`;
    this.router.navigate([path]);
  }
}
