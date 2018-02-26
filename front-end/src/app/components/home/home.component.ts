import {Component, OnDestroy, OnInit} from '@angular/core';
import {DomSanitizer} from "@angular/platform-browser";
import {ActivatedRoute, Router} from "@angular/router";
import {Auth} from '../../security/model/Auth';
import {Subject} from 'rxjs/Subject';

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, OnDestroy {

    title = 'LingBook';
    // src = `https://embed.ted.com/talks/patricia_kuhl_the_linguistic_genius_of_babies`;
    // src = `https://www.youtube.com/embed/WEnrfhygOQY`;
    src = 'https://www.youtube.com/embed/6a6vVIdQBd0';

    iframe = `<iframe width="560" height="315" src="https://www.youtube.com/embed/6a6vVIdQBd0" frameborder="0" allowfullscreen></iframe>`;
    auth: Auth;

    constructor(private sanitizer: DomSanitizer, private activatedRoute: ActivatedRoute, private router: Router) {
    }

    ngOnInit() {
        this.auth = this.activatedRoute.snapshot.data['authResolver'];
    }

    navigateToLogin() {
        console.log(' Navigate to Login');
        try {
            this.router.navigateByUrl('login');
        } catch (err) {
            console.log(err);
        }

    }

    ngOnDestroy(): void {

    }

    getSrc() {
        return this.sanitizer.bypassSecurityTrustResourceUrl(this.src);
    }

}
