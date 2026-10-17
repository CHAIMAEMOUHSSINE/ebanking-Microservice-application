import { Component } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {inject} from 'vitest';
import {Account, AccountListState, RequestStatus} from '../../../model/account.model';
import {catchError, map, Observable, of} from 'rxjs';

@Component({
  selector: 'app-accounts',
  imports: [],
  templateUrl: './accounts.html',
  styleUrl: './accounts.css',
})
export class Accounts {

  private http=inject(HttpClient);

  accounts$: Observable<AccountListState> = this.http
    .get<Account[]>('http://localhost:9999/EBANK-SERVICE/accounts')
    .pipe(
      map((accounts) => ({ accounts, status: RequestStatus.SUCCESS })),
      catchError((err) =>
        of({ status: RequestStatus.ERROR, errorMessage: err.statusText })
      )
    );
}
