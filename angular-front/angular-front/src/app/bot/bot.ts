import { Component, ElementRef, inject, signal, viewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import {
  HttpClient,
  HttpDownloadProgressEvent,
  HttpEventType,
  HttpResponse,
} from '@angular/common/http';
import { MarkdownComponent } from 'ngx-markdown';

interface Message {
  role: 'user' | 'bot';
  content: string;
}

@Component({
  selector: 'app-bot',
  imports: [FormsModule, MarkdownComponent],
  templateUrl: './bot.html',
  styleUrl: './bot.css',
})
export class Bot {
  private http = inject(HttpClient);
  private botUrl = 'http://localhost:9999/EBANK-BOT';

  chatBox = viewChild<ElementRef<HTMLDivElement>>('chatBox');

  query = '';
  sending = signal(false);
  messages = signal<Message[]>([]);

  suggestions = [
    'Liste des comptes bancaires',
    'Liste des clients',
    'Comptes du client 1',
  ];

  useSuggestion(text: string) {
    this.query = text;
    this.send();
  }

  send() {
    const q = this.query.trim();
    if (!q || this.sending()) return;

    this.messages.update((m) => [
      ...m,
      { role: 'user', content: q },
      { role: 'bot', content: '' },
    ]);
    this.query = '';
    this.sending.set(true);
    this.scrollDown();

    this.http
      .get(this.botUrl + '/chatStream?query=' + encodeURIComponent(q), {
        responseType: 'text',
        observe: 'events',
        reportProgress: true,
      })
      .subscribe({
        next: (event) => {
          if (event.type === HttpEventType.DownloadProgress) {
            this.setLastBot((event as HttpDownloadProgressEvent).partialText ?? '');
          } else if (event.type === HttpEventType.Response) {
            this.setLastBot((event as HttpResponse<string>).body ?? '');
          }
        },
        error: () => {
          this.setLastBot('Erreur : impossible de joindre le bot.');
          this.sending.set(false);
        },
        complete: () => this.sending.set(false),
      });
  }

  private setLastBot(raw: string) {
    const content = this.parseSse(raw);
    this.messages.update((list) => {
      const copy = [...list];
      copy[copy.length - 1] = { role: 'bot', content };
      return copy;
    });
    this.scrollDown();
  }

  // If the endpoint uses text/event-stream, strip the "data:" prefixes
  private parseSse(raw: string): string {
    if (!raw.startsWith('data:')) return raw;
    return raw
      .split('\n\n')
      .map((evt) =>
        evt
          .split('\n')
          .filter((l) => l.startsWith('data:'))
          .map((l) => l.slice(5))
          .join('\n')
      )
      .join('');
  }

  private scrollDown() {
    setTimeout(() => {
      const el = this.chatBox()?.nativeElement;
      if (el) el.scrollTop = el.scrollHeight;
    });
  }
}
