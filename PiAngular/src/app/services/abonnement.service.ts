import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

const BASE_URL = 'http://localhost:8081/Abonnement/Abonnement';

@Injectable({
  providedIn: 'root'
})
export class AbonnementService {
  constructor(private http: HttpClient) {}

  getAll(): Observable<any[]> {
    return this.http.get<any[]>(`${BASE_URL}/retrieveAllAbonnements`);
  }

  getById(id: number): Observable<any> {
    return this.http.get<any>(`${BASE_URL}/retrieveAbonnement/${id}`);
  }

  add(abonnement: any): Observable<any> {
    return this.http.post<any>(`${BASE_URL}/addAbonnement`, abonnement);
  }

  archive(id: number): Observable<any> {
    return this.http.put(`${BASE_URL}/archiveAbonnement/${id}`, {});
  }

  restore(id: number): Observable<any> {
    return this.http.put(`${BASE_URL}/restoreAbonnement/${id}`, {});
  }

  getPaged(page = 0, size = 10, sortBy = 'dateCreation', direction = 'asc'): Observable<any> {
    return this.http.get<any>(`${BASE_URL}/retrieveAllAbonnementsPaged?page=${page}&size=${size}&sortBy=${sortBy}&direction=${direction}`);
  }
  search(keyword: string, page = 0, size = 10, sortBy = 'dateCreation', direction = 'asc'): Observable<any> {
    return this.http.get<any>(`${BASE_URL}/search?keyword=${keyword}&page=${page}&size=${size}&sortBy=${sortBy}&direction=${direction}`);
  }
  getArchived(): Observable<any[]> {
    return this.http.get<any[]>(`${BASE_URL}/retrieveArchivedAbonnements`);
  }
  
}
