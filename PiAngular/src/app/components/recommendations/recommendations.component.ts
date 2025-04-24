import {Component, OnInit} from '@angular/core';
import {RecomendationService} from "../../services/recomendation.service";
import {MenuService} from "../../backoff/pages/menu/menu.service";

@Component({
  selector: 'app-recommendations',
  templateUrl: './recommendations.component.html',
  styleUrls: ['./recommendations.component.css']
})
export class RecommendationsComponent implements OnInit {
  menus: any[] = [];

  constructor(private recommendationService: RecomendationService, private menuService: MenuService) {
  }

  groupedMenus: any[][] = [];

  ngOnInit() {
    this.recommendationService.getRecommendations().subscribe(recommendations => {
      const ids = recommendations.map((rec: { idMenu: any; }) => rec.idMenu);

      ids.forEach((id: number) => {
        this.menuService.getMenuById(id).subscribe(full => {
          this.menus.push(full);
          this.groupMenus(); // regroup on every load
        });
      });
    });
  }

  groupMenus() {
    const chunkSize = 3;
    this.groupedMenus = [];
    for (let i = 0; i < this.menus.length; i += chunkSize) {
      this.groupedMenus.push(this.menus.slice(i, i + chunkSize));
    }


  }
}
