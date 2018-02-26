import {EventEmitter, Injectable, OnInit, Output} from '@angular/core';
import {GoogleOauth2Service} from "./google-oauth2.service";
import {FacebookAuthService} from "./facebook-auth.service";
import {Auth} from "./model/Auth";
import {IAuthResponse} from "./oauth/iauth-response";
import {IUserProfile} from "./oauth/iuser-profile";
import {AppComponent} from "../app.component";
import {Headers, Http, RequestOptions, Response} from "@angular/http";
import {Router} from "@angular/router";
import {HeaderBuilder} from "../utils/builders";
import {Constants} from "../utils/constants";

@Injectable()
export class OAuthService implements OnInit {

    private static AUTH: Auth;
    authProvider: string;
    private urlVerify = AppComponent.BACKEND_API + "/oauth2/verify";
    private urlSignIn = AppComponent.BACKEND_API + "/oauth2/sign-in";
    private urlSignUp = AppComponent.BACKEND_API + "/oauth2/sign-up";
    private urlSignOut = AppComponent.BACKEND_API + "/oauth2/sign-out";

    auth: Auth;
    @Output()
    authEmitter: EventEmitter<Auth> = new EventEmitter();
    loginModeEmitter: EventEmitter<string> = new EventEmitter();

    constructor(private googleAuthService: GoogleOauth2Service,
                private facebookAuthService: FacebookAuthService,
                private http: Http,
                private router: Router) {

    }

    ngOnInit(): void {

    }

    getAuth(): Promise<Auth> {
        // console.log(' getAuth ');
        if (this.isTokenActive()) {
            // console.log('Oauth2Service getAuth() 1');
            return new Promise((resolve, reject) => {
                resolve(OAuthService.AUTH);
            });
        } else {
            return new Promise((resolve, reject) => {

                this.facebookAuthService.getToken().then(token => {
                    // console.log('Oauth2Service getAuth() 5:  token = ' + token);
                    this.authProvider = Constants.FACEBOOK;
                    this.retrieveInfo(token, resolve, reject);
                }).catch((e1) => {
                    // console.log('Oauth2Service getAuth() 6');
                    this.googleAuthService.getToken().then(token => {
                        // console.log('Oauth2Service getAuth() Google');
                        this.authProvider = Constants.GOOGLE;
                        this.retrieveInfo(token, resolve, reject);
                        // console.log(token);
                    }).catch(e2 => {
                        // console.log(' getAuth()  Google Reject ');
                        reject(null);
                    });
                });
                // }

            });

        }
    }

    getInstantAuth(): Auth {
        return OAuthService.AUTH;
    }

    isAuthenticated(): boolean {
        return OAuthService.AUTH != null;
    }

    get authResponse(): IAuthResponse {
        // console.log(' OauthService called ');
        if (this.isAuthenticated()) {
            if (this.authProvider === Constants.GOOGLE) {
                return this.googleAuthService.getAuthResponse();
            } else if (this.authProvider === Constants.FACEBOOK) {
                return this.facebookAuthService.getAuthResponse();
            }
        }
        return null;
    }

    getUserProfile(): IUserProfile {
        // if (this.isAuthenticated()) {
        if (this.authProvider === Constants.GOOGLE) {
            return this.googleAuthService.getUserProfile();
        } else if (this.authProvider === Constants.FACEBOOK) {
            return this.facebookAuthService.getUserProfile();
        }
        // }
        return null;
    }

    // login(provider: string): Promise<Auth> {
    //
    //     if (provider === Constants.FACEBOOK) {
    //         this.authProvider = Constants.FACEBOOK;
    //         return new Promise((resolver, reject) => {
    //             this.facebookAuthService.logIn().then(auth => {
    //                 this.authEmitter.emit(auth);
    //                 resolver(auth);
    //             }).then(() => reject(null));
    //         });
    //     } else {
    //         this.authProvider = Constants.GOOGLE;
    //         return new Promise((resolver, reject) => {
    //             this.googleAuthService.logIn().then(auth => {
    //                 // console.log(' Emit Auth');
    //                 this.authEmitter.emit(auth);
    //                 resolver(auth);
    //             }).then(() => reject(null));
    //         });
    //     }
    // }

    signIn(provider: string): Promise<Auth> {
        if (provider === Constants.FACEBOOK) {
            this.authProvider = Constants.FACEBOOK;
            return new Promise((resolve, reject) => {
                this.facebookAuthService.signIn().then(token => {
                    this.retrieveInfo(token, resolve, reject);
                }).catch(() => {
                    reject(null);
                    // console.log('Reject ==>>> ');
                });
            });
        } else {
            this.authProvider = Constants.GOOGLE;
            return new Promise((resolve, reject) => {
                this.googleAuthService.signIn().then(token => {
                    this.retrieveInfo(token, resolve, reject);
                }).catch(() => reject(null));
            });
        }
    }

    logOut(reRouteUrl: string) {
        if (this.googleAuthService.isAuthenticated()) {
            this.googleAuthService.logOut(reRouteUrl);
        }
        if (this.facebookAuthService.isAuthenticated()) {
            this.facebookAuthService.logOut(reRouteUrl);
        }
    }

    signOut(reRouteUrl: string) {
        if (this.isAuthenticated()) {
            const headers: Headers = new HeaderBuilder().append(Constants.ACCESS_TOKEN, OAuthService.AUTH.accessToken).build();
            const options = new RequestOptions({headers: headers});
            this.http.get(this.urlSignOut, options).subscribe((res) => {
                const result = res.json();
                if (result.message != null) {
                    // console.log(result.message);
                    OAuthService.AUTH = null;
                    if (this.authProvider === Constants.GOOGLE) {
                        // console.log('Google Sign Out');
                        this.googleAuthService.logOut(reRouteUrl);
                    }
                    if (this.authProvider === Constants.FACEBOOK) {
                        // console.log('Facebook Sign Out');
                        this.facebookAuthService.logOut(reRouteUrl);
                    }
                }
            });
        }
    }

    private signInToLingbook(accessToken: string): Promise<Auth> {

        return new Promise((resolve, reject) => {
            const headers: Headers = new HeaderBuilder().append(Constants.ACCESS_TOKEN, accessToken).build();
            const options = new RequestOptions({headers: headers});
            // console.log(' signInToLingbook');
            this.http.get(this.urlSignIn, options).subscribe((response: Response) => {
                // console.log(' signInToLingbook');
                const obj: any = <any>response.json();
                if (obj.message === 'success') {
                    if (obj.reason === 'user_not_found') {
                        this.router.navigate(['login'], {queryParams: {mode: 'sign-up'}});
                        reject('sign-up');
                    } else {
                        OAuthService.AUTH = <Auth> JSON.parse(obj.auth);
                        // console.log(OAuthService.AUTH);
                        OAuthService.AUTH.accessToken = accessToken;
                        // Emit Auth so that the navbar can update user info
                        this.authEmitter.emit(OAuthService.AUTH);
                        // console.log(OAuthService.AUTH.accessToken);
                        resolve(OAuthService.AUTH);
                    }
                } else {
                    if (obj.reason === 'user_not_found') {
                        this.loginModeEmitter.emit('sign-up');
                        // console.log(obj.message);
                        reject(obj.message);
                    } else {
                        reject(null);
                    }
                }
            });
        });
    }

    private retrieveInfo(token: string, resolve, reject) {
        // console.log('retrieveInfo');
        this.signInToLingbook(token).then((auth: Auth) => {
            // console.log('retrieveInfo');
            OAuthService.AUTH = auth;
            //
            const expireIn = auth.expiresAt - Math.floor(Date.now() / 1000) - 30;
            setTimeout(() => {
                this.getAuth().then(a => OAuthService.AUTH = a).catch(() => {
                });
            }, expireIn);
            //
            resolve(auth);
            // console.log('retrieveInfo');
        }).catch(message => reject(message));
    }

    public signUp(provider: string): Promise<Auth> {
        if (provider === Constants.FACEBOOK) {
            this.authProvider = Constants.FACEBOOK;
            return new Promise((resolve, reject) => {
                this.facebookAuthService.signIn().then(token => {
                    this.signUpWrapper(token, resolve, reject);
                }).catch(() => {
                    reject(null);
                    // console.log('Reject ==>>> ');
                });
            });
        } else {
            this.authProvider = Constants.GOOGLE;
            return new Promise((resolve, reject) => {
                this.googleAuthService.signIn().then(token => {
                    this.signUpWrapper(token, resolve, reject);
                }).catch(() => reject(null));
            });
        }
    }

    private signUpLingbook(accessToken: string): Promise<Auth> {
        return new Promise((resolve, reject) => {
            const headers = new Headers({'Content-Type': 'application/json'});
            headers.append('Access-Control-Allow-Origin', AppComponent.ORIGIN_URL);
            headers.append('access_token', accessToken);
            console.log(accessToken);
            const options = new RequestOptions({headers: headers});
            this.http.get(this.urlSignUp, options).subscribe((response: Response) => {
                const obj: any = response.json();
                if (obj.message === 'user_already_existed') {
                    this.loginModeEmitter.emit('sign-in');
                    reject('sign-in');
                } else {
                    console.log("obj.auth" + obj.auth);
                    resolve(<Auth>JSON.parse(obj.auth));
                }
            });
        });
    }

    private signUpWrapper(token, resolve, reject) {
        this.signUpLingbook(token).then(auth => {
            resolve(auth);
        }).catch(error => {
            reject(error);
        });
    }

    private isTokenActive(): boolean {
        return OAuthService.AUTH != null && OAuthService.AUTH.expiresAt > Math.floor(Date.now() / 1000);
    }

    public getOptionsHeaders(): RequestOptions {

        if (this.isAuthenticated()) {
            const headers = new HeaderBuilder().append('access_token', OAuthService.AUTH.accessToken).build();
            return new RequestOptions({headers: headers});
        }
    }

    public getOptionsHeadersPromise(): Promise<RequestOptions> {

        return new Promise((resolve, reject) => {
            this.getAuth().then(auth => {
                // console.log(' getOptionsHeadersPromise');
                const headers = new HeaderBuilder().append('access_token', auth.accessToken).build();
                resolve(new RequestOptions({headers: headers}));
            }).catch(err => reject(null));
        });
    }
}
