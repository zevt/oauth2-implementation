import {Injectable, OnInit} from '@angular/core';
import {FacebookService, LoginOptions, LoginResponse, LoginStatus} from "ngx-facebook";
import {Router} from "@angular/router";
import {Http} from "@angular/http";
import {AuthProvider} from "./oauth/auth-provider";
import {UserProfile} from "./oauth/user-profile";
import {AuthResponse as CustomAuthResponse} from "./oauth/auth-response";
import {Auth} from "./model/Auth";
import {IUserProfile} from "./oauth/iuser-profile";
import {IAuthResponse} from "./oauth/iauth-response";
import {Constants} from "../utils/constants";

/**
 * @see https://www.npmjs.com/package/ng2-facebook-sdk
 */
@Injectable()
export class FacebookAuthService implements AuthProvider, OnInit {
    profileUrl = '/me?fields=name,email';

    userProfile: UserProfile;
    authResponse: CustomAuthResponse;
    constructor(private facebook: FacebookService,
                private router: Router,
                private http: Http) {
        facebook.init({
            appId: 'secretsecretsecretsecret',
            xfbml: true,
            status: true,
            cookie: true,
            version: 'v2.9',
        });

        FB.AppEvents.logPageView();
        this.facebook.getLoginStatus().then((loginStatus: LoginStatus) => {
            if (loginStatus && loginStatus.authResponse) {
                const authRes = loginStatus.authResponse;
                this.authResponse = new CustomAuthResponse(authRes.accessToken, 0, authRes.expiresIn);
                this.facebook.api(this.profileUrl).then(res => {
                    this.userProfile = new UserProfile(res.name, res.email);
                });
            }
            // alert(' ABC ');
        });
    }

    /**
     *  Check if user alread login;
     */
    ngOnInit(): void {
    }

    /**
     *
     * @returns {Promise<Auth>}
     */

    getToken(): Promise<string> {
        return new Promise((resolve, reject) => {
            if (this.facebook.getLoginStatus() != null) {
                this.facebook.getLoginStatus().then((loginStatus: LoginStatus) => {
                    if (!!loginStatus && !!loginStatus.authResponse) {
                        // const authRes = loginStatus.authResponse;
                        // this.setAuthResolve(Constants.FACEBOOK + authRes.accessToken, resolve, reject);
                        resolve(Constants.FACEBOOK + loginStatus.authResponse.accessToken);
                    } else {
                        reject(null);
                    }
                }).catch(() => reject(null));
            } else {
                // console.log(' facebook getAuth 10');
                reject(null);
            }
        });
    }

    isAuthenticated(): boolean {
        return (this.getAuthResponse() !== null);
    }

    getUserProfile(): IUserProfile {
        return this.userProfile;
    }

    getAuthResponse(): IAuthResponse {
        // console.log(" getAuthResponse --> " + this.authResponse);
        if (this.authResponse == null || this.facebook.getAuthResponse() == null) {
            // console.log(" getAuthResponse --> null");
            return null;
        } else {

            if (this.authResponse.getExpiresAt() < curentEpochSeconds() ||
                this.authResponse.getAccessToken() !== this.facebook.getAuthResponse().accessToken) {
                this.facebook.getAuthResponse();
                const authRes = this.facebook.getAuthResponse();
                this.authResponse = new CustomAuthResponse(authRes.accessToken, 0, authRes.expiresIn);
                // this.retrieveAuthResponse(authRes, null);
            }
        }
        return this.authResponse;
    }

    /**
     *
     * @returns {Promise<Auth>}
     */

    signIn(): Promise<string> {
        const loginOptions: LoginOptions = {
            enable_profile_selector: true,
            return_scopes: true,
            // scope: 'public_profile,user_friends,email,pages_show_list'
            scope: 'public_profile,email'
        };

        return new Promise((resolve, reject) => {
            this.facebook.login(loginOptions).then((response: LoginResponse) => {
                if (response.status === 'connected') {
                    // this.setAuthResolve(response.authResponse.accessToken, resolve, reject);
                    resolve(Constants.FACEBOOK + response.authResponse.accessToken);
                } else {
                    reject(null);
                }
            });
        });
    }

    private retrieveUserProfile() {
        this.facebook.api(this.profileUrl).then(res => {
            this.userProfile = new UserProfile(res.name, res.email);
        });
    }

    logOut(reRouteUrl: string) {
        /**
         * @see https://stackoverflow.com/questions/10807122/how-can-i-force-a-facebook-access-token-to-expire
         * Alternative: user DELETE method to "https://graph.facebook.com/v2.7/me/permissions?access_token=${token}{"success":true}"
         */

            // this.facebook.logout().then( () => {});

        const path = 'me/permissions?success:true';
        // console.log('Facebook Attempt Logout ');
        this.facebook.api(path, 'delete').then(response => {
            if (response.success === true) {
                this.userProfile = null;
                this.authResponse = null;
                // console.log('reRouteUrl: ' + reRouteUrl);
                this.router.navigateByUrl(reRouteUrl).catch(error => {
                    // console.log(' failed to reroute');
                });
            }
        }).catch((error) => {
            // console.log(error);
            this.router.navigateByUrl(reRouteUrl).catch(er => {
            });
        });
    }

    private adjustAuthResponse() {
        const authRes = this.facebook.getAuthResponse();
        this.authResponse = new CustomAuthResponse(authRes.accessToken, 0, authRes.expiresIn);
    }

}

declare let FB: any;

function curentEpochSeconds(): number {
    return Math.floor(Date.now() / 1000);
}
