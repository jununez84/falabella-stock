import { Image } from './image';

export class Product {
  id: number = 0;
  sku: string = '';
  name: string = '';
  brand: string = '';
  size?: number;
  price: number = 0.0;
  images: Image[] = [];
}
