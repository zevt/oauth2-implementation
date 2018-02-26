import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Auth} from '../../../security/model/Auth';
import {AuthResolver} from '../../../resolver/AuthResolver';

@Component({
    selector: 'app-task',
    templateUrl: './task.component.html',
    styleUrls: ['./task.component.css']
})
export class TaskComponent implements OnInit, OnDestroy {
    auth: Auth;
    constructor(private activatedRoute: ActivatedRoute, private authResolver: AuthResolver) {
    }

    ngOnInit() {
        this.auth = this.activatedRoute.snapshot.data['authResolver'];
    }

    ngOnDestroy(): void {
    }

}
