import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';

import { Router } from '@angular/router';
import { AfterViewInit, OnDestroy, ViewChild } from '@angular/core';
import { Subject } from 'rxjs';
import { GlobalsService } from '../globals.service';
import { FormsModule } from '@angular/forms';

import { StompService } from '@stomp/ng2-stompjs';
import { Message, StompHeaders } from '@stomp/stompjs';
import { Subscription, Observable } from 'rxjs';
import Swal from 'sweetalert2';

@Component({
 selector: 'app-game-page',
 templateUrl: './game-page.component.html',
 styleUrls: ['./game-page.component.scss']
})
export class GamePageComponent implements OnInit {
 // Info being passed to and from server
 payload: object;

 // Hardcode a QuestionBean
 // jsonString: string = '{"isMultipleChoice":true, "category":"geography", "difficulty":"easy", "question":"Which small country is located between the borders of France and Spain?", "correctIndex":2, "answers":["Vatican City", "San Marino", "Andorra", "Lichtenstein"]}';

// properties of each question
 questionObj: object;
 isMultipleChoice: boolean;
 category: string;
 difficulty: number;
 question: string;
 correctIndex: number;
 answers: string[];

 // Hardcore time
 currentTime = 20;

 currentQuestionNumber: number;
 totalQuestions: number;
 progressPercent: number;
 progress; // For the progress bar

 // Player properties
 playerId: number;
 didAnswer = false;
 displayMsg: string;
 points: number;
 // Game properties
 players = 0;
 currentAnswers = 0;

 public userId;

 // Stream of messages
 private data_subscription: Subscription;
 public data_observable: Observable<Message>;
// Stream of chats
public chat_subscription: Subscription;
public chat_observable: Observable<Message>;
 private _stompService: StompService;
 // Subscription status
 public subscribed = false;

 public chats = [];

 StompConfig = {
   url: this.globals.getSocketUrl() +  'join-game-session',
   headers: {},
   heartbeat_in: 0, // Typical value 0 - disabled
   heartbeat_out: 20000, // Typical value 20000 - every 20 seconds
   reconnect_delay: 0,
   debug: true // Will log diagnostics on console
 };

 constructor(
   private sanitizer: DomSanitizer,
   public router: Router,
   public globals: GlobalsService
 ) { }

 ngOnInit() {
  
   if (!this.globals.getLobbyKey()) {  // If you don't have a lobby key, navigate home
     this.router.navigate(['']);
   } else {                          // Else start game
//      this.loadQuestion();
//      this.loadProgressBar();
      this.userId = this.globals.getUserId();

     this.connect();
     this.playerId = Number(this.globals.getUserId()); // Set this player's id
     if (this.globals.getIsLeader()) {
       this.initGame();
     }
   }
 }

 public onChatUpdate = (chat_observable: Message) => {

  const payload = JSON.parse(chat_observable.body);
  console.log(payload);
  const message_user = payload['username'];
  const message_body = payload['message'];
  const userId = payload['userId'];
  const time = payload['time'];

  this.chats.push({
    username: message_user,
    message: message_body,
    id: userId,
    time: time
  });
}

 public onDataUpdate = (data_observable: Message) => {
   const payload = JSON.parse(data_observable.body);
   console.log(payload);

   if (this.question !== this.decodeHtml(payload['currentQuestion']['question'])) {
     this.currentQuestionNumber = payload['currentQuestionNumber'];
     this.totalQuestions = payload['numberOfQuestions'];
     this.question = this.decodeHtml(payload['currentQuestion']['question']);
     this.answers = payload['currentQuestion']['answers'];
     Swal.close();

     for (let i = 0; i < this.answers.length; i++) {
       this.answers[i] = this.decodeHtml(this.answers[i]);
     }

     this.isMultipleChoice = payload['currentQuestion']['multipleChoice'];
     this.correctIndex = payload['currentQuestion']['correctIndex'];
     this.didAnswer = false;
     const diff = payload['currentQuestion']['difficulty'];
     this.setDifficulty(diff);
     this.loadProgressBar();
   }

   // These need to be updated every refresh
   this.currentAnswers = payload['numberOfAnswers'];
   this.currentTime = payload['currentCountDown'];
   this.players = payload['players'];

   // END OF GAME LOGIC
   if (payload['status'] === 2) {
     this.unsubscribe();
     console.log('game over');
     // Sort the top scores
     const playerList = payload['topScores'];
     let playerObj;
     let place: number;
     let endMsg: string;
     playerList.sort((a, b) => a.score < b.score ? -1 : a.score > b.score ? 1 : 0);
     let index = 1;
     // Find this player's object and set final place
     for (const p of playerList) {
       if (p.playerId === this.playerId) {
         playerObj = p;
         place = index;
       }
       index++;
     }
     // Populate end message
     if (place === 1){
       endMsg = `<span class="label label-success" style="font-size: 20px;">` +
                `You won 1st place! Score: ${playerObj.score}, ` +
                `Max Streak: ${playerObj.maxStreak}</span>`;
      }
      else {
       endMsg = `1st place winner: ${playerList[0].username}<br>` +
                `You got ${this.ordinal_suffix_of(place)} place!<br>` +
                `Score: ${playerObj.score}<br>Max Streak: ${playerObj.maxStreak}<br>`;
     }

     Swal({
      title: 'Game Over!',
      html: endMsg,
      width: 600,
      backdrop: `
        rgba(0,0,123,0.4)
        url("https://sweetalert2.github.io/images/nyan-cat.gif")
        center left
        no-repeat
         ` 
      }).then(() => {
        window.location.href = '/';
      });
   }
 }

 // Decode out html character references
 public decodeHtml(html) {
   return $('<div>').html(html).text();
}

ordinal_suffix_of(i: number): string {
 var j = i % 10,
     k = i % 100;
 if (j == 1 && k != 11) {
     return i + 'st';
 }
 if (j == 2 && k != 12) {
     return i + 'nd';
 }
 if (j == 3 && k != 13) {
     return i + 'rd';
 }
 return i + 'th';
}

 initGame() {
   const self = this;
   $.ajax({
     url: self.globals.getApiUrl() + 'start-game',
     method: 'POST',
     data: {
       key: self.globals.getLobbyKey()
     },
     crossDomain: true,
     xhrFields: { withCredentials: true },
     success: function (res) {
       console.log('Game has started');
     }
   });
 }

   public sendMessage() {

    const username = this.globals.getUsername();
    const message = $('#message_input').val();
    $('#message_input').val('');

    this._stompService.publish('/game-update/' + this.globals.getLobbyKey() + '/recive-chat-game', JSON.stringify({
      username: username,
      message: message,
      userId: this.globals.getUserId()
    }));
  }

 sendAnswer() {
   const self = this;
   $.ajax({
     url: self.globals.getApiUrl() + 'game-update',
     method: 'POST',
     data: {
       playerId: self.globals.getUserId(),
       lobbyKey: self.globals.getLobbyKey(),
       points: this.points
     },
     crossDomain: true,
     xhrFields: { withCredentials: true },
     success: function (res) {
       console.log('Submitted Answer');
     }
   });
 }

 // Web sockets
 connect() {
   this._stompService = new StompService(this.StompConfig);
   this._stompService.initAndConnect();

   this.data_observable = this._stompService.subscribe('/send-game-update/' + this.globals.getLobbyKey() + '/get-game-data');
   this.data_subscription = this.data_observable.subscribe(this.onDataUpdate);
   
   
   this.chat_observable = this._stompService.subscribe('/send-game-update/' + this.globals.getLobbyKey().toLowerCase() + '/send-chat');
   this.chat_subscription = this.chat_observable.subscribe(this.onChatUpdate);

   
   
   this.subscribed = true;

   // Only ping the server if it's the leader
   if (this.globals.getIsLeader()) {
     this.startPingingServer(this);
   }
 }

 public startPingingServer(self) {
   if (self.subscribed) {
     console.log('ping');
     self._stompService.publish('/game-update/' + self.globals.getLobbyKey() + '/get-game-data', '');
     setInterval(this.startPingingServer, 500, self);
   }
 }

 public unsubscribe() {
   this.data_subscription = null;
   this.data_observable = null;
   this.subscribed = false;
   this._stompService.deactivate();
 }
  loadQuestion(){
   this.questionObj = this.payload['QuestionBean'];
   console.log(this.questionObj);
   this.isMultipleChoice = this.questionObj['isMultipleChoice'];
   this.category = this.questionObj['category'];
//    this.setDifficulty();
   this.question = this.questionObj['question'];
   this.correctIndex = this.questionObj['correctIndex'];
   this.answers = this.questionObj['answers'];
 }

 // Set difficulty multiplier
 setDifficulty(diff: string) {
   if (diff === 'easy') {
     this.difficulty = 30;
   }
   if (diff === 'medium') {
     this.difficulty = 45;
   }
   if (diff === 'hard') {
     this.difficulty = 60;
   }
 }

 loadProgressBar(){
   this.progressPercent = 100*(this.currentQuestionNumber/this.totalQuestions);
   this.progress = this.sanitizer.bypassSecurityTrustStyle(`width: ${this.progressPercent}%`);
 }


 // audio files

 incorrectAudio() {
  const audio = new Audio();
  audio.src = '../../../assets/audio/Price-is-right-losing-horn.mp3';
  audio.load();
  audio.play();
}
 correctAudio() {
  const audio = new Audio();
  audio.src = '../../../assets/audio/applause-8.mp3';
  audio.load();
  audio.play();
}

 checkAnswer(playerAnswer: number){
   this.didAnswer = true;
   if (playerAnswer === this.correctIndex){
     this.points = this.calculatePoints();
     this.displayMsg = `Correct! +${this.points} pts.`;
     this.correctAudio();
     Swal(
       'Good job!',
       `Correct! +${this.points} pts.`,
       'success'
     ).then(() => {
       Swal.close();
     });
   } else {
     this.displayMsg = `Wrong. The Correct answer is: ${this.answers[this.correctIndex]}`;
     this.points = 0;
     this.incorrectAudio();
     Swal(
       'Good Luck Next Time!',
       `Wrong. The Correct answer is: ${this.answers[this.correctIndex]}`,
       'error'
     ).then(() => {
       Swal.close();
     });
   }
   this.sendAnswer();
 }

 calculatePoints(): number{
   const correctPoints = this.difficulty;
   const timeBonus = 1 + (this.currentTime/20);
   return Math.round(correctPoints*timeBonus);
 }
}
