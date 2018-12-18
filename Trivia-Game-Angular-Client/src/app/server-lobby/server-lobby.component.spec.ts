import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ServerLobbyComponent } from './server-lobby.component';

describe('ServerLobbyComponent', () => {
  let component: ServerLobbyComponent;
  let fixture: ComponentFixture<ServerLobbyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ServerLobbyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ServerLobbyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
