import { Component } from '@angular/core';
import {Stacktrace} from "../../domains/Stacktrace";
import {Link} from "../../domains/Link";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  collapsed = true;
  title = 'Organisation';
  lastStacktrace:Stacktrace | undefined;
  links: Link[] = this.getLinks();

  constructor(
    private router: Router,
    private route: ActivatedRoute
  ) {
    let currentUrl = window.location.href;
    currentUrl = currentUrl.substring(currentUrl.lastIndexOf("/") + 1);
    this.activateCurrentLink(currentUrl);
  }

  navigate(navigationPath: string) {
    this.activateCurrentLink(navigationPath);
    this.router.navigate([navigationPath], {relativeTo: this.route});
  }

  search() {

  }

  private activateCurrentLink(navigationPath?: string) {
    this.links.forEach(link => {
      link.active = false;

      if (link.title === navigationPath) {
        link.active = true;
      }
    });
  }

  private getLinks():Link[] {
    return [
      new Link("Dashboard", true),
      new Link("Organisation")
    ]
  }
}
