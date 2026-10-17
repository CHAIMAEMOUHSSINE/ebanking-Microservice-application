import { Component, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AsyncPipe, DatePipe, DecimalPipe } from '@angular/common';
import { catchError, map, Observable, of } from 'rxjs';


@Component({
  selector: 'app-accounts',
  imports: [AsyncPipe, DatePipe, DecimalPipe],
  templateUrl: './accounts.html',
  styleUrl: './accounts.css',
})
export class Accounts {
  private http = inject(HttpClient);
  public loadingService = inject(LoadingService);
  protected readonly RequestStatus = RequestStatus;

  accounts$: Observable<AccountListState> = this.http
    .get<Account[]>('http://localhost:9999/EBANK-SERVICE/accounts')
    .pipe(
      map((accounts) => ({ accounts, status: RequestStatus.SUCCESS })),
      catchError((err) =>
        of({ status: RequestStatus.ERROR, errorMessage: err.statusText })
      )
    );

  totalBalance(accounts: Account[] = []): number {
    return accounts.reduce((sum, a) => sum + (a.balance ?? 0), 0);
  }

  countType(accounts: Account[] = [], type: string): number {
    return accounts.filter((a) => a.type === type).length;
  }

  typeLabel(type?: string): string {
    return type === 'SAVING_ACCOUNT' ? 'Savings' : 'Current';
  }

  typeClass(type?: string): string {
    return type === 'SAVING_ACCOUNT' ? 'bg-success' : 'bg-primary';
  }
}
