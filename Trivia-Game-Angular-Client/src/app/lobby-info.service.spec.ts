import { TestBed } from '@angular/core/testing';

import { LobbyInfoService } from './lobby-info.service';

describe('LobbyInfoService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: LobbyInfoService = TestBed.get(LobbyInfoService);
    expect(service).toBeTruthy();
  });
});
