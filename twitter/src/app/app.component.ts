import { AfterViewInit, ChangeDetectorRef, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { HttpService, Tweet, Comment } from './http.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements AfterViewInit{
  tweets!: Tweet[]
  port: string = '8080'
  height: number = window.innerHeight
  observer!: ResizeObserver
  @ViewChild('content') contentDiv!: ElementRef<HTMLDivElement>

  constructor(private http: HttpService, private ref: ChangeDetectorRef){}

  async ngAfterViewInit(){
    this.elementObserver()
    await this.setTweets()
  }

  elementObserver() {
    this.observer = new ResizeObserver(entries => {
      for (let entry of entries) {
        const cr = entry.contentRect
        this.height = (cr.height < window.innerHeight) ? window.innerHeight : cr.height
        this.ref.detectChanges()
      }
    })

    this.observer.observe(this.contentDiv.nativeElement)
  }

  async setTweets(){
    this.tweets = (<Tweet[]>await this.http.get(`http://127.0.0.1:${this.port}/tweet`))

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

  setHeight(){
    return `${this.height + 200}px`
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
