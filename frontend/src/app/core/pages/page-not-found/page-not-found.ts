import { HttpStatusCode } from '@angular/common/http';
import { Component } from '@angular/core';

@Component({
  selector: 'app-page-not-found',
  imports: [],
  templateUrl: './page-not-found.html',
  styleUrl: './page-not-found.scss',
})
export class PageNotFound {
  public NOT_FOUND_ERROR_CODE: number = HttpStatusCode.NotFound;
  public PAGE_NOT_FOUND_MESSAGE: string = $localize`Page not found`;
}
