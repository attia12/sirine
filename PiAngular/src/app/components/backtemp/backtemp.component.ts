import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-backtemp',
  templateUrl: './backtemp.component.html',
  styleUrls: ['./backtemp.component.css']
})
export class BacktempComponent implements OnInit {

  ngOnInit(): void {
    // Supprimer toutes les classes précédentes du <body> (ex: front)
    document.body.className = '';

    // Optionnel : Ajouter une classe dédiée au layout back si besoin
    document.body.classList.add('argon-dashboard');
  }

}
