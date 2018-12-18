import { Component, OnInit } from '@angular/core';
import { DataTableDirective, DataTablesModule } from 'angular-datatables';
import { Router } from '@angular/router';
import { AfterViewInit, OnDestroy, ViewChild } from '@angular/core';
import { Subject } from 'rxjs';
import { GlobalsService } from '../globals.service';
import { FormsModule } from '@angular/forms';


import { StompService } from '@stomp/ng2-stompjs';
import { Message, StompHeaders } from '@stomp/stompjs';
import { Subscription, Observable } from 'rxjs';

@Component({
  selector: 'app-server-lobby',
  templateUrl: './server-lobby.component.html',
  styleUrls: ['./server-lobby.component.scss']
})
export class ServerLobbyComponent implements OnInit, AfterViewInit, OnDestroy {

  @ViewChild(DataTableDirective)

  dtElement: DataTableDirective;

  dtOptions: DataTables.Settings;

  dtTrigger: any = new Subject();

  maxPages: number;
  currentPage: number;

  lobbyInf: any [];

  currentDataHash = '0';

  username: String;

  // Stream of messages
  private data_subscription: Subscription;
  public data_observable: Observable<Message>;

  private _stompService: StompService;

  // Subscription status
  public subscribed = false;

  StompConfig = {
    url: 'ws://127.0.0.1:8080/TriviaTownesServer/select-lobby-hash',
    headers: {},
    heartbeat_in: 0, // Typical value 0 - disabled
    heartbeat_out: 20000, // Typical value 20000 - every 20 seconds
    reconnect_delay: 0,
    debug: true // Will log diagnostics on console
  };

  disconnect() {
    this._stompService.deactivate();
  }

  connect() {
    this._stompService = new StompService(this.StompConfig);
    this._stompService.initAndConnect();

    this.data_observable = this._stompService.subscribe('/lobbies-hash/' + this.globals.getCategory().toLowerCase() + '/get-lobby-data');
    this.data_subscription = this.data_observable.subscribe(this.onDataUpdate);
    this.subscribed = true;

    this.startPingingServer(this);
  }

  public startPingingServer(self) {

      if(self.subscribed){
      console.log('ping');
      console.log(self.currentDataHash);
      self._stompService.publish('/lobby-hash-update/' + self.globals.getCategory().toLowerCase() + '/get-lobby-data', '');
 
      setInterval(self.startPingingServer, 1000, self);
      }
    }

  public onDataUpdate = (data_observable: Message) => {
    console.log('Hello');
    console.log(data_observable.body);
    console.log(this.currentDataHash);

    if (this.currentDataHash !== data_observable.body) {
      this.currentDataHash = data_observable.body;
      this.rerender();
    }
  }

  public unsubscribe() {
    if (!this.subscribed) {
      return;
    }
    this.data_subscription = null;
    this.data_observable = null;
    this.subscribed = false;
  }

  constructor(
    public router: Router,
    public globals: GlobalsService
  ) { }

  ngAfterViewInit(): void {

    const self = this;
    this.dtTrigger.next();

    $('#datatable-custom-search').on('input', function() {
      self.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
        dtInstance.search($(this).val() + '').draw();
      });
    });
    $('#datatable-custom-prev-btn').on('click', function () {
      self.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
        dtInstance.page('previous').draw('page');
      });
    });
    $('#datatable-custom-next-btn').on('click', function () {
      self.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
        dtInstance.page('next').draw('page');
      });
    });

  }

  ngOnDestroy(): void {

    this.dtTrigger.unsubscribe();
    this.unsubscribe();
  }

  rerender(): void {

    const self = this;

    this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {

      // Destroy the table first
      dtInstance.destroy();
      // Call the dtTrigger to rerender again
      this.dtTrigger.next();
    });
  }

  ngOnInit() {
    if(this.globals.getCategory() === undefined){
      this.router.navigate(['']);
    } else {
    console.log(this.globals.getCategory());
    this.loadServers();
    //this.connect();
    }
  }

  joinLobby(key: any){

    var self = this;

    $.ajax ({
      url: self.globals.getApiUrl() + '/join-waiting',
      data: {
        lobbyKey: key,
        username: self.username
      },
      method: 'POST',
      crossDomain: true,
      xhrFields: { withCredentials: true },
      success: function (res) {
        console.log(res);
        self.globals.setLobbyKey(res['lobbyId']);
        self.globals.setUsername(res['userId']);
        self.globals.setUsername(self.username);
        self.globals.setUserId(res['userId']);
        self.globals.setGameCategory(res['category']);
        self.globals.setLobbyQuestions(res['questions']);
        self.globals.setLobbyName(res['lobbyName']);
        self.router.navigate(['waiting']);
      },
      error: function (res) {
        console.log('error....');
        console.log(res);
        alert('game session is full, please pick another lobby....');
      }
    });
  }

  loadServers(): void {

    const self = this;

    this.dtOptions = {
      ajax: {
        url: self.globals.getApiUrl() + 'lobby-data/' + self.globals.getCategory().toLowerCase() + '/data/',
        method: 'GET',
        crossDomain: true,
        xhrFields: { withCredentials: true },
      },
      columns: [
        {
          title: 'Name',
          data: 'name'
        },
        {
          title: 'Category',
          data: 'category'
        },
        {
          title: 'Current Players',
          data: 'players'
        },
        {
          title: 'Max Players',
          data: 'maxPlayers'
        }
      ],
      rowCallback: (row: Node, data: any[] | Object, index: number) => {
        const self = this;
        // Unbind first in order to avoid any duplicate handler
        // (see https://github.com/l-lin/angular-datatables/issues/87)
        $('td', row).unbind('click');
        $('td', row).bind('click', () => {
         // console.log(data);

          if(self.username === undefined){
            alert('Please enter a username!');
            return;
          }

          self.joinLobby(data['key']);

          //self.someClickHandler(data);
        });
        return row;
      }
    };

    // hides default search, pagination stuff
    this.dtOptions.dom = '<t>';

    this.dtOptions.pageLength = 10;

    this.dtOptions.drawCallback = function () {

      self.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {

        self.maxPages = dtInstance.page.info().pages;
        self.currentPage = dtInstance.page.info().page + 1;

        $('#datatable-custom-next-btn').prop('enabled', false);
        $('#datatable-custom-prev-btn').prop('enabled', false);

        if(self.currentPage === self.maxPages){
          $('#datatable-custom-next-btn').prop('enabled', true);
        }
        if(self.currentPage === 1){
          $('#datatable-custom-prev-btn').prop('enabled', true);
        }
        if (self.maxPages === 0) {
          $('#datatable-custom-next-btn').prop('enabled', true);
          $('#datatable-custom-prev-btn').prop('enabled', true);
          $('#datatable-custom-page-label').val('0/0');
        } else {
          $('#datatable-custom-page-label').prop('disabled', true);
          $('#datatable-custom-page-label').val(self.currentPage + '/' + self.maxPages);
        }
      });


    };
  }
}



