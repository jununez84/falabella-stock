import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Product } from '../models/product';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private apiUrl: string = 'http://localhost:8080/api/products';

  constructor(private http: HttpClient) {}

  public getAll(): Observable<Product[]> {
    return this.http.get<Product[]>(this.apiUrl);
  }

  public save(product: Product): Observable<Product> {
    if (product.sku) {
      const url = `${this.apiUrl}/${product.sku}`;
      return this.http.put<Product>(this.apiUrl + '/' + product.sku, product);
    } else {
      return this.http.post<Product>(this.apiUrl, product);
    }
  }

  public getById(id: number): Observable<Product> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.get<Product>(url);
  }
}
