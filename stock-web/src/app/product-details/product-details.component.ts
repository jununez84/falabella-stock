import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from '../shared/models/product';
import { ProductService } from '../shared/services/product.service';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.scss'],
})
export class ProductDetailsComponent implements OnInit {
  product: Product;
  loading: boolean;

  constructor(
    private productService: ProductService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const sku = this.route.snapshot.paramMap.get('sku');

    if (sku) {
      this.getProduct(sku);
    } else {
      this.product = new Product();
    }
  }

  cancel() {
    this.goToProducts();
  }

  getProduct(sku) {
    this.loading = true;
    this.productService.getById(sku).subscribe((product) => {
      this.product = product;
      this.loading = false;
    });
  }

  save() {
    this.productService.save(this.product).subscribe(() => this.goToProducts());
  }

  private goToProducts() {
    this.router.navigate(['/products']);
  }
}
