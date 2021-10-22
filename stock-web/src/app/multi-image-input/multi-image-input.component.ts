import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Image } from '../shared/models/image';

@Component({
  selector: 'app-multi-image-input',
  templateUrl: './multi-image-input.component.html',
  styleUrls: ['./multi-image-input.component.scss'],
})
export class MultiImageInputComponent implements OnInit {
  constructor() {}

  @Input() images: Image[];
  @Output() imagesChange: EventEmitter<Image[]> = new EventEmitter();

  imageUrl: string;

  ngOnInit(): void {}

  keyUpEnter(event: KeyboardEvent): void {
    if (event.key === 'Enter' && !!this.imageUrl) {
      this.addImage();
      this.clearUrlInput();
    }
  }

  removeImage(index: number) {
    this.images.splice(index, 1);
    this.imagesChange.emit(this.images);
  }

  setAsMain(index: number) {
    let image = this.images[index];
    if (image.type !== 'MAIN') {
      this.uncheckCurrentMainImage();
      image.type = 'MAIN';
      this.imagesChange.emit(this.images);
    }
  }

  blurUrlInput() {
    if (!!this.imageUrl) {
      this.addImage();
      this.clearUrlInput();
    }
  }

  private addImage() {
    this.images.push(<Image>{
      url: this.imageUrl,
      type: this.images.length == 0 ? 'MAIN' : 'SECONDARY',
    });
    this.imagesChange.emit(this.images);
  }

  private clearUrlInput() {
    this.imageUrl = '';
  }

  private uncheckCurrentMainImage() {
    let mainImage = this.images.find((x) => x.type === 'MAIN');
    mainImage.type = 'SECONDARY';
  }
}
