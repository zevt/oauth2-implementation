import {AfterContentInit, Component, OnChanges, OnDestroy, OnInit, SimpleChanges} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {OAuthService} from "../../security/oauth.service";
import {Auth} from "../../security/model/Auth";
import {User} from "../../security/model/User";

@Component({
    selector: 'app-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: ['./navbar.component.css']
})

export class NavbarComponent implements OnInit, AfterContentInit, OnDestroy {
    user: User;

    constructor(private authService: OAuthService, public router: Router, private activatedRoute: ActivatedRoute) {
    }

    ngOnInit() {

        this.authService.getAuth().then(auth => {
            this.user = auth.user;
        }).catch(err => {
        });

        this.authService.authEmitter.subscribe((auth: Auth) => {
            if (auth != null) {
                this.user = auth.user;
            }
        });
    }

    ngOnDestroy() {
        // prevent memory leak when component is destroyed
        this.authService.authEmitter.unsubscribe();
    }

    ngAfterContentInit(): void {
        // console.log(' Current Url:' + this.router.routerState.snapshot.url);
    }

    signIn() {
        this.router.navigateByUrl('login');
    }

    signOut() {
        this.user = null;
        // this.authService.logOut('login');
        this.authService.signOut('login');
    }

    update() {
        // if (this.authService.authEmitter != null) {
        //     this.authService.authEmitter.subscribe((auth: Auth) => {
        //         if (auth != null) {
        //             this.user = auth.user;
        //             console.log(' subscribe userProfile at Navbar. User = ' + this.user.roles);
        //         }
        //     });
        // }
    }

    getUserName() {
        if (this.user != null) {
            return this.user.name;
        }
        return '';
    }
}
