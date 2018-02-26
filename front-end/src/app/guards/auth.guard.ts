import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs/Observable';
import {AuthResolver} from "../resolver/AuthResolver";
import {OAuthService} from "../security/oauth.service";

@Injectable()
export class AuthGuard implements CanActivate {

    constructor(private auth: OAuthService, private router: Router,
                private authResolver: AuthResolver) {
    }

    canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {

        if (!this.auth.isAuthenticated()) {
            this.router.navigate(['login'], {queryParams: {beforeSignInUrl: state.url}});
            return false;
        }
        return true;
    }
}
