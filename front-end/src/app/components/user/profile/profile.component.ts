import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {ToastrService} from "../../../common/toastr.service";
import {OAuthService} from "../../../security/oauth.service";

@Component({
    selector: 'app-profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.css']
})

export class ProfileComponent implements OnInit {
    firstNameForm: FormControl = new FormControl();
    lastNameForm: FormControl = new FormControl();
    profileForm: FormGroup;

    constructor(private  _auth: OAuthService, private router: Router, private toastr: ToastrService) {

    }

    ngOnInit() {
        this.profileForm = new FormGroup({
            firstName: this.firstNameForm,
            lastName: this.lastNameForm
        });

        // let user: User;
        // if (this._auth.isAuthenticated()) {
            // user = this._auth.getUserProfile();
            // this.firstNameForm.setValue(user.firstName);
            // this.firstNameForm.setValidators([Validators.required, Validators.pattern('[a-zA-Z]*')]);
            // this.lastNameForm.setValue(user.lastName);
            // this.lastNameForm.setValidators([Validators.required, Validators.pattern('[a-zA-Z]*')]);
        // }
    }

    cancel() {
        this.router.navigate(['active-learning']);
    }

    saveProfile(data) {
        if (this.profileForm.valid) {
            // const user: User = this._auth.getUserProfile();
            // user.firstName = data['firstName'];
            // user.lastName = data['lastName'];
            // this._auth.saveUser(user);
            this.router.navigate(['active-learning']);
            this.toastr.success(' Profile was successfully saved');
        } else {
            this.toastr.warning(' Profile was not saved');
        }
    }

    isValidatedFirstName(): boolean {
        const firstName = this.profileForm.controls['firstName'];
        if (firstName) {
            return firstName.valid || firstName.untouched;
        }
        return true;
    }

    isValidatedFirstNameRequired() {
        const firstName = this.profileForm.controls['firstName'];
        if (firstName && firstName.errors) {
            return !firstName.errors.required || firstName.untouched;
        }
        return true;
    }

    isValidatedFirstNamePattern() {
        const firstName = this.profileForm.controls['firstName'];
        if (firstName && firstName.errors) {
            return !firstName.errors.pattern || firstName.untouched;
        }
        return true;
    }

    isValidatedLastName(): boolean {
        const lastName = this.profileForm.controls['lastName'];
        if (lastName) {
            return lastName.valid || lastName.untouched;
        }
        return true;
    }

    isValidatedLastNameRequired(): boolean {
        const lastName = this.profileForm.controls['lastName'];
        if (lastName && lastName.errors) {
            return !lastName.errors.required || lastName.untouched;
        }
        return true;
    }

    isValidatedLastNamePattern(): boolean {
        const lastName = this.profileForm.controls['lastName'];
        if (lastName && lastName.errors) {
            return !lastName.errors.pattern || lastName.untouched;
        }
        return true;
    }

}
