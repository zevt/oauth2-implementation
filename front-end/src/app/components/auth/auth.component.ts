import {Component} from "@angular/core";
import {OAuthService} from "../../security/oauth.service";

@Component({
    selector: 'app-auth',
    template: '<span></span>',
    styles: [],
})

export class AuthComponent {

    constructor(private authService: OAuthService) {
    }

    getInstantAuth() {
        return this.authService.getInstantAuth();
    }
}
