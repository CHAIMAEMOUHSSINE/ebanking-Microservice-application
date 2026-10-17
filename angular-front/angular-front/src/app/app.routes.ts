import { Routes } from '@angular/router';
import { Accounts } from './accounts/accounts';
import { Bot } from './bot/bot';

export const routes: Routes = [
  { path: 'accounts', component: Accounts },
  { path: 'bot', component: Bot },
];
