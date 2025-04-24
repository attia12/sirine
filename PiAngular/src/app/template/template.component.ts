import { Component, AfterViewInit } from '@angular/core';

declare var AOS: any;
declare var GLightbox: any;
declare var Swiper: any;

@Component({
  selector: 'app-template',
  templateUrl: './template.component.html',
  styleUrls: ['./template.component.css']
})
export class TemplateComponent implements AfterViewInit {

  ngAfterViewInit(): void {
    // AOS animations
    AOS.init();

    // Lightbox
    GLightbox({ selector: '.glightbox' });

    // Swiper
    new Swiper('.init-swiper', {
      loop: true,
      speed: 600,
      autoplay: { delay: 5000 },
      slidesPerView: 'auto',
      pagination: {
        el: '.swiper-pagination',
        type: 'bullets',
        clickable: true
      },
      breakpoints: {
        320: { slidesPerView: 2, spaceBetween: 40 },
        480: { slidesPerView: 3, spaceBetween: 60 },
        640: { slidesPerView: 4, spaceBetween: 80 },
        992: { slidesPerView: 5, spaceBetween: 120 },
        1200: { slidesPerView: 6, spaceBetween: 120 }
      }
    });
  }
}
