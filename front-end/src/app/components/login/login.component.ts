/**
 * @see https://www.npmjs.com/package/ng2-facebook-sdk
 * @see https://zyra.github.io/ngx-facebook/
 */
import {Component, OnDestroy, OnInit} from '@angular/core';
import {Http} from "@angular/http";
import 'rxjs/Rx';
import {ActivatedRoute, Router} from "@angular/router";
import {OAuthService} from "../../security/oauth.service";
import {AuthResolver} from '../../resolver/AuthResolver';
import {Subject} from 'rxjs/Subject';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {
    display = 'none';
    mode = 'sign-in';

    constructor(private http: Http,
                private authService: OAuthService,
                private router: Router,
                private activatedRoute: ActivatedRoute,
                private authResolver: AuthResolver) {
        this.mode = 'sign-in';
    }

    ngOnInit() {
        // console.log(' Attempt to go to Login');
        const auth = this.activatedRoute.snapshot.data['authResolver'];

        if (auth != null) {
            // console.log(auth);
            if (this.router != null) {
                this.router.navigateByUrl(this.getRedirectUrl());
            }
        } else {
            this.display = 'block';
        }

        this.mode = 'sign-in';

        if (this.authService.loginModeEmitter != null) {
            try {
                this.authService.loginModeEmitter.subscribe(mode => {
                    this.mode = mode;
                });
            } catch (e) {
                this.mode = 'sign-in';
            }
        } else {
            this.mode = 'sign-in';
        }

    }

    ngOnDestroy(): void {
        this.authService.authEmitter.first().subscribe(e => {});
        this.authService.loginModeEmitter.first().subscribe(e => {});

        console.log(' Destroy Login Component');
    }

    signIn(provider: string) {
        this.authService.signIn(provider).then(auth => {
            // console.log(auth);
            this.router.navigateByUrl(this.getRedirectUrl());
            this.display = 'none';
        }).catch(error => {
            // console.log(' Cannot sign In');
        });
    }

    signUp(provider: string) {
        this.authService.signUp(provider).then(auth => {
            this.router.navigateByUrl('welcome');
        }).catch(error => {
        });

    }

    private getRedirectUrl(): string {
        let url: string;
        if (this.activatedRoute != null && this.activatedRoute.snapshot != null) {
            url = this.activatedRoute.snapshot.queryParams['beforeSignInUrl'];
        }
        if (url == null) {
            url = '';
        }
        return url;
    }

}



