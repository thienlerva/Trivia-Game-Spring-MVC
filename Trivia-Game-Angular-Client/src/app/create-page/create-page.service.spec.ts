import { TestBed } from '@angular/core/testing';

import { CreatePageService } from './create-page.service';

describe('CreatePageService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CreatePageService = TestBed.get(CreatePageService);
    expect(service).toBeTruthy();
  });
});
