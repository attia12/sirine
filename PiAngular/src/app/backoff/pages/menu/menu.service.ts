import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class MenuService {

  private apiUrl = 'http://localhost:8080/Menu/menus';

  constructor(private http: HttpClient) {}


  getAllMenus(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}`);
  }
  associatePlatsToMenu(menuId: number, platIds: number[]): Observable<any> {
    const requestBody = { platIds };
    return this.http.post(`${this.apiUrl}/${menuId}/associatePlats`, requestBody,{responseType:'text'});
  }

  getMenuById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }


  createMenu(menu: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/add`, menu);
  }


  updateMenu(id: number, menu: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}`, menu);
  }


  deleteMenu(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
  getConfirmedMenus(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/menus/confirmed`);
  }

  toggleLikeDislike(menuId: number, userId: number, status: string): Observable<any> {
    return this.http.put(`${this.apiUrl}/${menuId}/like/${userId}?status=${status}`, {}, { responseType: 'text' });
  }
  addComment(menuId: number, userId: number, commentText: string): Observable<any> {

    return this.http.post(`${this.apiUrl}/${menuId}/comment/${userId}`, { text: commentText });
  }

  getUserLikeStatus(menuId: number, userId: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${menuId}/like-status?userId=${userId}`);
  }
  getCommentsByMenuId(menuId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/${menuId}/with-comments`);
  }
  updateComment(comment: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${comment.menu.idMenu}/comment/${comment.id}`, comment);
  }

  // Delete a comment
  deleteComment(menuId: number, commentId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${menuId}/comment/${commentId}`);
  }

  searchMenus(query: string) {
    return this.http.get<any[]>(`${this.apiUrl}/search`, {
      params: { query}
    });

  }
}
