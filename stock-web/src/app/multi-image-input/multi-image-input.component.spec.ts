import { CommonModule } from '@angular/common';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { Image } from '../shared/models/image';

import { MultiImageInputComponent } from './multi-image-input.component';

describe('MultiImageInputComponent', () => {
  let component: MultiImageInputComponent;
  let fixture: ComponentFixture<MultiImageInputComponent>;

  const images: Image[] = [
    { id: 1, url: 'img1.png', type: 'MAIN' },
    { id: 2, url: 'img2.png', type: 'SECONDARY' },
  ];

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [MultiImageInputComponent],
      imports: [CommonModule, FormsModule],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MultiImageInputComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('tests keyUpEnter', () => {
    it('should create a new image', () => {
      component.images = [...images];
      component.imageUrl = 'img3.png';
      const event = { key: 'Enter' };

      component.keyUpEnter(<KeyboardEvent>event);

      expect(component.imageUrl).toEqual('');
      expect(component.images[2].url).toEqual('img3.png');
    });
  });

  describe('tests blurUrlInput', () => {
    it('should create a new image', () => {
      component.images = [...images];
      component.imageUrl = 'img4.png';

      component.blurUrlInput();

      expect(component.imageUrl).toEqual('');
      expect(component.images[2].url).toEqual('img4.png');
    });
  });

  describe('tests removeImage', () => {
    it('should remote a secondary image', () => {
      component.images = [...images];

      component.removeImage(1);

      expect(component.images.length).toEqual(1);
    });
  });

  describe('tests setAsMain', () => {
    it('should set a second image as main', () => {
      component.images = [...images];

      component.setAsMain(1);

      expect(component.images[0].type).toEqual('SECONDARY');
      expect(component.images[1].type).toEqual('MAIN');
    });
  });
});
