import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {HttpModule} from "@angular/http";
import {ToastrService} from "./common/toastr.service";
import {TOASTR_TOKEN} from "./common/toastrOpq";
import {JQuery_TOKEN} from "./common/jquery.service";
import {NavbarComponent} from "./components/navbar/navbar.component";
import {RouterModule} from "@angular/router";
import {appRoute} from "./router/appRouter";
import {ErrorComponent} from "./components/error/error.component";
import {HomeComponent} from "./components/home/home.component";
import {SanitizeHtmlPipe} from './pipe/sanitize-html.pipe';
import {SanitizeSrcPipe} from './pipe/sanitize-src.pipe';
import {jQueryToken} from "./common/jquery2.service";
import {FormsModule} from "@angular/forms";
import {FacebookModule, FacebookService} from "ngx-facebook";
import {LoginComponent} from "./components/login/login.component";
import {OAuthService} from "./security/oauth.service";
import {FacebookAuthService} from "./security/facebook-auth.service";
import {GoogleOauth2Service} from "./security/google-oauth2.service";
import {AuthGuard} from "./guards/auth.guard";
import {AuthComponent} from "./components/auth/auth.component";
import {AuthResolver} from "./resolver/AuthResolver";
import {LoginGuard} from "./guards/login.guard";
import {MaterialDesignModule} from './modules/material-design.module';
import {JDDSecretComponentComponent} from './components/jddsecret-component/jddsecret-component.component';

export declare let toastr: ToastrService;
export declare let jQuery: Object;

@NgModule({
    declarations: [
        JDDSecretComponentComponent,
        AppComponent,
        NavbarComponent,
        ErrorComponent,
        HomeComponent,
        SanitizeHtmlPipe,
        SanitizeSrcPipe,
        LoginComponent,
        AuthComponent
    ],
    imports: [
        BrowserModule,
        FormsModule,
        HttpModule,
        RouterModule.forRoot(appRoute),
        FacebookModule.forRoot(),
        MaterialDesignModule
    ],
    providers: [{provide: TOASTR_TOKEN, useValue: toastr},
        {
            provide: JQuery_TOKEN, useValue: jQuery
        },
        {
            provide: jQueryToken,
            useValue: jQuery
        },
        ToastrService,
        OAuthService,
        FacebookAuthService,
        GoogleOauth2Service,
        FacebookService,
        AuthGuard,
        LoginGuard,
        AuthResolver
    ],
    bootstrap: [AppComponent]
})

export class AppModule {
}
