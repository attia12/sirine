import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class RecomendationService {

  private apiUrl = 'http://localhost:8080/Menu/api/recommendations/recommend';

  constructor(private http: HttpClient) {}

  getRecommendations(): Observable<any> {
    return this.http.post(this.apiUrl,{});
  }
}
