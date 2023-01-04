import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';

export interface Comment {
  id: number,
  content: string,
  likes: number
} 

export interface Tweet { 
  id: number
  content: string,
  likes: number,
  showComments: boolean,
  comments: Comment[]
}

@Injectable({
  providedIn: 'root'
})
export class HttpService {

  constructor(private http: HttpClient) { }

  public async get(url: string){
    return await firstValueFrom( this.http.get( url ) )
  }

  public async post(url: string, body: any){
    return await firstValueFrom( this.http.post(url, body) )
  }

  public async delete(url: string){
    return await firstValueFrom( this.http.delete(url) )
  }
}
