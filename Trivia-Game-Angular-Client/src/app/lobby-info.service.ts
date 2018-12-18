import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LobbyInfoService {

  private apiName: String;
  private lobbyName: String;
  private category: String;
  private lobbyStatus: String;
  private seats: Number;


  constructor() { }

  public getAPI(): String {

    return this.apiName;

  }

  public getlobbyName(): String {

    return this.lobbyName;

  }

  public setlobbyName(lobbyName): void {

    this.lobbyName = lobbyName;

  }

  public getCategory(): String {

    return this.category;

  }

  public setCategory(category): void {

    this.category = this.category;

  }

  public setlobbyStatus(): String {

    return this.lobbyStatus;

  }

  public getlobbyStatus(lobbyStatus): void {

    this.lobbyStatus = lobbyStatus;

  }

  public getSeats(): Number {

    return this.seats;

  }

  public setSeats(seats): void {

    this.seats = seats;

  }

}
