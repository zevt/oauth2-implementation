import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {Observable} from "rxjs/Observable";
import {Auth} from "../security/model/Auth";
import {OAuthService} from "../security/oauth.service";

@Injectable()
export class AuthResolver implements Resolve<Auth> {

    constructor(private oauthService: OAuthService) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Auth> | Promise<Auth> | Auth {

        return Observable.create(observer => {
            this.oauthService.getAuth().then(auth => {
                observer.next(auth);
                observer.complete();
            }).catch(er => {
                observer.next(null);
                observer.complete();
            });
        });

    }
}
