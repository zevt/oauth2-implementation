import {Component} from '@angular/core';
import {RequestOptions, Headers} from "@angular/http";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})

export class AppComponent {
    static ORIGIN_URL = 'http://localhost:4200';
    static BACKEND_API = 'http://localhost:8080';
    static options;

    title = 'JDD  Secret';

    constructor() {
        const headers = new Headers({'Content-Type': 'application/json'});
        headers.append('Access-Control-Allow-Origin', AppComponent.ORIGIN_URL);
        AppComponent.options = new RequestOptions({headers: headers});
    }
}
