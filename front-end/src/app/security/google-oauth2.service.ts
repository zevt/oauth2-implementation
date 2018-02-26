import {Injectable, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AuthProvider} from "./oauth/auth-provider";
import {IAuthResponse} from "./oauth/iauth-response";
import {AuthResponse} from "./oauth/auth-response";
import {IUserProfile} from "./oauth/iuser-profile";
import {Auth} from "./model/Auth";
import {AppComponent} from "../app.component";
import {Constants} from "../utils/constants";
import {User} from "./model/User";

@Injectable()
export class GoogleOauth2Service implements OnInit, AuthProvider {

    static GOOGLEAUTH;
    static AUTH_RESPONSE;

    constructor(private router: Router) {
        this.gapiInit('');
    }

    gapiInit(redirect: string) {
        redirect = AppComponent.ORIGIN_URL + '/' + redirect;
        redirect = redirect.trim();
        gapi.load('auth2', function () {
            GoogleOauth2Service.GOOGLEAUTH = gapi.auth2.init({
                client_id: 'secretsecretsecretsecret',
                // fetch_basic_profile: true,
                scope: 'openid profile email',
                ux_mode: 'popup',
                // ux_mode: 'redirect',
                redirect_uri: redirect,
                // prompt: 'consent'
                prompt: 'none'
            }).then((googleAuth) => {
                    // console.log(' Initializing and consume Promise googleAuth ');
                    GoogleOauth2Service.GOOGLEAUTH = googleAuth;
                    if (googleAuth.isSignedIn && googleAuth.isSignedIn.get()) {
                        const googleUser = GoogleOauth2Service.GOOGLEAUTH.currentUser.get();
                        GoogleOauth2Service.AUTH_RESPONSE = googleUser.getAuthResponse(true);
                        // console.log(GoogleOauth2Service.AUTH_RESPONSE);
                    }
                },
            );
        });
    }

    /**
     * @external link
     * @see https://developers.google.com/identity/sign-in/web/reference#googleusergetauthresponseincludeauthorizationdata
     * @see https://developers.google.com/identity/protocols/OpenIDConnect#scope-param
     * @external link
     */

    ngOnInit(): void {

    }

    /**
     * Get Google AuthResponse Object
     * @returns {AuthResponse}
     */

    getAuthResponse(): IAuthResponse {
        if (this.isAuthenticated()) {
            const authResponse = GoogleOauth2Service.AUTH_RESPONSE;
            console.log(authResponse);
            return new AuthResponse(authResponse.id_token, Math.floor(authResponse.expires_at / 1000), 0);
        }
        return null;
    }

    getUserProfile(): IUserProfile {
        if (GoogleOauth2Service.GOOGLEAUTH) {
            if (GoogleOauth2Service.GOOGLEAUTH.isSignedIn && GoogleOauth2Service.GOOGLEAUTH.isSignedIn.get()) {
                return GoogleOauth2Service.GOOGLEAUTH.currentUser.get().getBasicProfile();
            }
        }
        return null;
    }

    isAuthenticated() {
        // console.log(GoogleOauth2Service.AUTH_RESPONSE );
        /**
         * Check if GoogleOauth2Service.AUTH_RESPONSE is null or it already expired
         */
        if (GoogleOauth2Service.AUTH_RESPONSE != null && GoogleOauth2Service.AUTH_RESPONSE.expires_at < Date.now()) {
            // if (gapi.auth2 != null) {
            // console.log('isSignedIn: ' + GoogleOauth2Service.GOOGLEAUTH.isSignedIn);
            if (GoogleOauth2Service.GOOGLEAUTH != null) {
                // const googleAuth = gapi.auth2.getAuthInstance();
                if (!!GoogleOauth2Service.GOOGLEAUTH.isSignedIn && !!GoogleOauth2Service.GOOGLEAUTH.currentUser) {
                    const googleUser = GoogleOauth2Service.GOOGLEAUTH.currentUser.get();
                    GoogleOauth2Service.AUTH_RESPONSE = googleUser.getAuthResponse(true);
                }
            } else {
                // console.log(' gapi.auth2 != null ');
            }
        }
        return GoogleOauth2Service.AUTH_RESPONSE != null && GoogleOauth2Service.AUTH_RESPONSE.expires_at > Date.now();
    }

    getAuth(): Promise<Auth> {

        console.log('Google getAuth: 1');
        if (this.isAuthenticated()) {
            console.log('Google getAuth: 2');
            const authResponse = GoogleOauth2Service.AUTH_RESPONSE;
            const basicProfile: IUserProfile = this.getUserProfile();
            const auth = new Auth(new User(basicProfile.getName(), null),
                Constants.GOOGLE + authResponse.id_token,
                Math.floor(authResponse.expires_at / 1000));
            return Promise.resolve(auth);
        } else {
            console.log('Google getAuth: 3');

            return new Promise(function (resolve, reject) {

                gapi.auth2.getAuthInstance().then((googleAuth) => {
                        console.log('Google getAuth: 5');
                        // const googleUser = googleAuth.currentUser.get();
                        // GoogleOauth2Service.AUTH_RESPONSE = googleUser.getAuthResponse(true);
                        GoogleOauth2Service.GOOGLEAUTH = googleAuth;
                        console.log('Google getAuth: 6');
                        try {
                            if (googleAuth.isSignedIn && googleAuth.isSignedIn.get()) {
                                console.log('Google getAuth: 7');
                                const googleUser = GoogleOauth2Service.GOOGLEAUTH.currentUser.get();
                                GoogleOauth2Service.AUTH_RESPONSE = googleUser.getAuthResponse(true);
                                if (GoogleOauth2Service.AUTH_RESPONSE != null) {
                                    /**
                                     * gapi.auth2.BasicProfile has the following methods:
                                     BasicProfile.getId()
                                     BasicProfile.getName()
                                     BasicProfile.getGivenName()
                                     BasicProfile.getFamilyName()
                                     BasicProfile.getImageUrl()
                                     BasicProfile.getEmail()
                                     */
                                    const authResponse = GoogleOauth2Service.AUTH_RESPONSE;
                                    const basicProfile = GoogleOauth2Service.GOOGLEAUTH.currentUser.get().getBasicProfile();
                                    const auth = new Auth(basicProfile,
                                        Constants.GOOGLE + authResponse.id_token,
                                        Math.floor(authResponse.expires_at / 1000));
                                    console.log('Google getAuth: 7.1');
                                    resolve(auth);
                                } else {
                                    console.log(' This is strange, not logIn but come here');
                                    reject('');
                                }

                            } else {
                                // console.log('Google getAuth: 8');
                                reject(null);
                                // return null;
                            }
                        } catch (er) {
                            reject(null);
                        }
                    },
                );
                // console.log('Google getAuth: 4');
            });
        }
    }

    private resolveGapiAuth2(resolve, reject) {

        gapi.auth2.init({
            client_id: '724422975832-jku02idmv3eh8jl0954fmenfhdacgotb.apps.googleusercontent.com',
            // fetch_basic_profile: true,
            scope: 'openid profile email',
            ux_mode: 'popup',
            // ux_mode: 'redirect',
            redirect_uri: AppComponent.ORIGIN_URL,
            // prompt: 'consent'
            prompt: 'none'
        }).then((googleAuth) => {
            // console.log('Google getAuth: 5');
            if (googleAuth.isSignedIn && googleAuth.isSignedIn.get()) {
                // console.log('Google getAuth: 7');
                const googleUser = googleAuth.currentUser.get(); // .getAuthResponse(true);
                GoogleOauth2Service.AUTH_RESPONSE = googleUser.getAuthResponse(true);
                if (GoogleOauth2Service.AUTH_RESPONSE != null) {
                    console.log(GoogleOauth2Service.AUTH_RESPONSE.id_token);
                    resolve(Constants.GOOGLE + GoogleOauth2Service.AUTH_RESPONSE.id_token);
                } else {
                    reject(null);
                }

            } else {
                // console.log('Google getAuth: 8');
                reject(null);
            }
        }).catch(() => {
            reject(null);
            // console.log('Google getToken Reject');
        });
    }

    /**
     *
     * @returns {Promise<Auth>}
     */
    logIn(): Promise<Auth> {
        return new Promise((resolve, reject) => {
            if (gapi.auth2 != null) {
                const googleAuth = gapi.auth2.getAuthInstance();
                const options = new gapi.auth2.SigninOptionsBuilder();
                // options.setFetchBasicProfile(true);
                options.setPrompt('select_account');
                options.setScope('profile').setScope('email');
                googleAuth.signIn(options)
                    .then(googleUser => {
                        GoogleOauth2Service.AUTH_RESPONSE = googleUser.getAuthResponse();
                        this.getUserProfile();
                        const auth = null;
                        resolve(auth);
                    });
            } else {
                reject(null);
            }
        });
    }

    /**
     * Return Promise of google oauth token
     * @returns {Promise<string>}
     */
    signIn(): Promise<string> {
        return new Promise((resolve, reject) => {
            if (gapi.auth2 != null) {
                const googleAuth = gapi.auth2.getAuthInstance();
                const options = new gapi.auth2.SigninOptionsBuilder();
                // options.setFetchBasicProfile(true);
                options.setPrompt('select_account');
                options.setScope('profile').setScope('email');
                googleAuth.signIn(options)
                    .then(googleUser => {
                        GoogleOauth2Service.AUTH_RESPONSE = googleUser.getAuthResponse();
                        this.getUserProfile();
                        const token = Constants.GOOGLE + GoogleOauth2Service.AUTH_RESPONSE.id_token;
                        // console.log('Google Token = ' + token);
                        resolve(token);
                    });
            } else {
                reject(null);
            }
        });
    }

    logOut(reRouteUrl: string) {
        const googleAuth = gapi.auth2.getAuthInstance();
        GoogleOauth2Service.AUTH_RESPONSE = null;
        if (googleAuth != null) {
            try {
                googleAuth.signOut().then(() => {
                    this.router.navigateByUrl(reRouteUrl).catch();
                }).catch(error => {
                    // console.log(error);
                });
            } catch (error) {
                // console.log(error);
            }
        }
    }

    getToken(): Promise<string> {

        // console.log('Google getAuth: 1');
        if (this.isAuthenticated()) {
            // console.log('Google getAuth: 2');
            return Promise.resolve(Constants.GOOGLE + GoogleOauth2Service.AUTH_RESPONSE.id_token);
        } else {
            // console.log('Google getAuth: 3');
            return new Promise(function (resolve, reject) {
                gapi.auth2.getAuthInstance().then((googleAuth) => {
                        // console.log('Google getAuth: 5');
                        GoogleOauth2Service.GOOGLEAUTH = googleAuth;
                        // console.log('Google getAuth: 6');
                        try {
                            if (googleAuth.isSignedIn && googleAuth.isSignedIn.get()) {
                                // console.log('Google getAuth: 7');
                                const googleUser = GoogleOauth2Service.GOOGLEAUTH.currentUser.get();
                                GoogleOauth2Service.AUTH_RESPONSE = googleUser.getAuthResponse(true);
                                if (GoogleOauth2Service.AUTH_RESPONSE != null) {
                                    // console.log('Google getAuth: 7.1');
                                    resolve(Constants.GOOGLE + GoogleOauth2Service.AUTH_RESPONSE.id_token);
                                } else {
                                    reject(null);
                                }
                            } else {
                                // console.log('Google getAuth: 8');
                                reject(null);
                            }
                        } catch (er) {
                            reject(null);
                        }
                    },
                );
            });
        }
    }
}

declare const gapi: any;
