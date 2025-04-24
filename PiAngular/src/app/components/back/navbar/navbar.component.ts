import { Component, OnInit, ElementRef } from '@angular/core';
import { ROUTES } from '../sidebar/sidebar.component';
import { Location } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  public focus: boolean = false;
  public listTitles: any[] = [];
  public location: Location;

  constructor(location: Location, private element: ElementRef, private router: Router) {
    this.location = location;
  }

  ngOnInit() {
    this.listTitles = ROUTES.filter(listTitle => listTitle);
  }

  getTitle() {
    let path = this.location.prepareExternalUrl(this.location.path());

    // Nettoyer les ancres (#)
    if (path.charAt(0) === '#') {
      path = path.slice(1);
    }

    // Chercher un titre qui correspond au chemin courant
    for (let item = 0; item < this.listTitles.length; item++) {
      if (path.includes(this.listTitles[item].path)) {
        return this.listTitles[item].title;
      }
    }

    return 'Dashboard';
  }
}
