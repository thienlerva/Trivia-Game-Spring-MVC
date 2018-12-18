import {Input, Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import anime from 'animejs';
import { GlobalsService } from '../globals.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-create-page',
  templateUrl: './create-page.component.html',
  styleUrls: ['./create-page.component.scss']
})
export class CreatePageComponent implements OnInit {
  selectedCategory: string;
  seats: string;
  questions: string;
  difficulty: string;
  // private = false;
  username: string;
  name: string;
  key: string;

  constructor(
      public router: Router,
      public globals: GlobalsService) { }

  ngOnInit () {
    this.newUser();
  }

  // call when a user hits landing page
  newUser() {

    $.ajax({
      url: this.globals.getApiUrl() + 'new-user',
      method: 'GET',
      crossDomain: true,
      xhrFields: { withCredentials: true },
      success: function (result) {
        console.log('Created Session');
      },
      error: function (result) {
        console.log('Something went wrong');
        console.log(result);
      }
    });
  }

  selectCategory(category) {
    this.selectedCategory = category;
    console.log(this.selectedCategory);
  }

  selectedSeats(num: number) {
    this.seats = num + '';
    console.log(this.seats);
  }

  selectedQuestions(num: number) {
    this.questions = num + '';
    console.log(this.questions);
  }

  selectedDifficulty(str: string) {
    this.difficulty = str + '';
    console.log(this.difficulty);
  }

  // privateOrPublic() {
  //   this.private = !this.private;
  //   console.log(this.private);
  // }

  lobbyName(str: string) {
    this.name = str;
    console.log(this.name);
  }

  setUsername (str: string) {
    this.username = str;
    console.log(this.username);
  }

  create() {
    const x = this;

    //x.selectedCategory = 'computer';
    x.name = 'Trivia Towens';
    x.seats = '20';
    x.questions = '3';
    x.username = 'Ross Boss';

    if (!(x.selectedCategory && x.seats && x.questions && x.name && x.username)) {
     alert('Missing values for game creation');
     return;
    }

    $.ajax({
      url: x.globals.getApiUrl() + 'create-game',
      method: 'POST',
      crossDomain: true,
      xhrFields: { withCredentials: true },
      data: {
        category: x.selectedCategory,
        seats: x.seats,
        questions: x.questions,
        username: x.username,
        name: x.name,
      },
      success: function (res) {
        console.log('** GAME CREATED **');
        console.log(res);
        console.log(res['lobbyId']);
        x.globals.setLobbyKey(res['lobbyId']);
        x.globals.setUsername(x.username);
        x.globals.setUserId(res['userId']);
        x.globals.setGameCategory(res['category']);
        x.globals.setLobbyQuestions(res['questions']);
        x.globals.setLobbyName(res['lobbyName']);
        x.globals.setIsLeader(true);
        x.router.navigate(['waiting']);
      },
      error: function (res) {
        console.log(this.data);
        alert('There was a problem connecting to lobby...');
      }
    });
  }
}
