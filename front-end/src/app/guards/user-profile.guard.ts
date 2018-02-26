import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs/Observable';
import {ToastrService} from "../common/toastr.service";
import {OAuthService} from "../security/oauth.service";

@Injectable()
export class UserProfileGuard implements CanActivate {
    constructor(private router: Router, private auth: OAuthService, private toastr: ToastrService) {
    }

    canActivate(next: ActivatedRouteSnapshot,
                state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
        if (!this.auth.getUserProfile()) {
            this.router.navigate(['user/login']);
            this.toastr.info(' Please login first before visiting profile');
            return false;
        }
        return true;
    }
}
