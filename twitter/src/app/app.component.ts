import { Component, OnInit } from '@angular/core';
import { HttpService, Tweet, Comment } from './http.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  tweets!: Tweet[]
  port: string = '8080'

  constructor(private http: HttpService){}

  async ngOnInit() {
    this.setTweets()
  }

  async setTweets(){
    this.tweets = (<Tweet[]>(<any>await this.http.get(`http://127.0.0.1:${this.port}/tweet`)).tweets)

    for(var i of this.tweets){
      i.showComments = false
    }
  }

  async setComments(item: any, overWrite: boolean = false): Promise<boolean>{
    if(item.comments == undefined || overWrite == true){
      item.comments     = <Comment[]>await this.http.get(`http://127.0.0.1:${this.port}/tweet/${item.id}/comment`)
      item.showComments = true
      return true
    }
    return false
  }

  async toggleComments(id: number){
    for(var i of this.tweets){
      if(i.id == id){
        if(await this.setComments(i) == false) {
               if(i.showComments == true) i.showComments = false
          else if(i.showComments == false) i.showComments = true
        }
      }
    }
  }

  tweetIndexById(id: number){
    var index = 0
    for(var i of this.tweets){
      if(i.id == id){
        break
      }
      index++
    }
    return index
  }

  commentIndexById(tid: number, cid: number){
    var index = 0
    for(var i of this.tweets){
      if(i.id == tid){
        for(var i2 of i.comments){
          if(i2.id == cid){
            break
          }
          index++
        }
      }
    }
    return index
  }

  async submitComment(comment: string, id: number){
    await this.http.post(`http://127.0.0.1:${this.port}/tweet/${id}/comment`, { content: comment })

    for(var i of this.tweets){
      if(i.id == id){
        await this.setComments(i, true)
        break
      }
    }
  }

  async submitTweet(tweet: string){
    await this.http.post(`http://127.0.0.1:${this.port}/tweet`, { content: tweet })
    await this.setTweets()
  }

  async likeTweet(id: number){
    await this.http.get(`http://127.0.0.1:${this.port}/tweet/${id}/like`)

    this.tweets[ this.tweetIndexById(id) ].likes++
  }

  async likeComment(tid: number, cid: number){
    await this.http.get(`http://127.0.0.1:${this.port}/tweet/${tid}/comment/${cid}/like`)

    this.tweets[ this.tweetIndexById(tid) ].comments[ this.commentIndexById(tid, cid) ].likes++
  }

  async deleteTweet(id: number){
    await this.http.delete(`http://127.0.0.1:${this.port}/tweet/${id}`)
    await this.setTweets()
  }
}