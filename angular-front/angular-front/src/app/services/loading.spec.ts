import { TestBed } from '@angular/core/testing';
import { Loading } from './loading';

describe('Loading', () => {
  let service: Loading;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Loading);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
