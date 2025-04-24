import { Component, OnInit } from '@angular/core';
import * as Chart from 'chart.js'; // ✅ pour Chart.js v2

// Core charts config
import {
  chartOptions,
  parseOptions,
  chartExample1,
  chartExample2
} from '../../variables/charts';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  public datasets: number[][] = [];
  public data: number[] = [];
  public salesChart: any;
  public clicked = true;
  public clicked1 = false;

  ngOnInit(): void {
    this.datasets = [
      [0, 20, 10, 30, 15, 40, 20, 60, 60],
      [0, 20, 5, 25, 10, 30, 15, 40, 40]
    ];
    this.data = this.datasets[0];

    // Appliquer les options globales Chart.js
    parseOptions(Chart as any, chartOptions());

    // Chart Orders
    const chartOrders = document.getElementById('chart-orders') as HTMLCanvasElement;
    if (chartOrders) {
      new (Chart as any)(chartOrders, {
        type: 'bar',
        options: chartExample2.options,
        data: chartExample2.data
      });
    }

    // Chart Sales
    const chartSales = document.getElementById('chart-sales') as HTMLCanvasElement;
    if (chartSales) {
      this.salesChart = new (Chart as any)(chartSales, {
        type: 'line',
        options: chartExample1.options,
        data: chartExample1.data
      });
    }
  }

  updateOptions(): void {
    if (this.salesChart) {
      this.salesChart.data.datasets[0].data = this.data;
      this.salesChart.update();
    }
  }
}
