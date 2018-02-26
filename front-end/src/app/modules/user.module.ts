import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule} from "@angular/router";
import {ProfileComponent} from "../components/user/profile/profile.component";
import {ReactiveFormsModule, FormsModule, } from "@angular/forms";
import {UserProfileGuard} from "../guards/user-profile.guard";
import {LoginComponent} from "../components/login/login.component";

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        // RouterModule.forChild(userRouter)
    ],
    declarations: [
        ProfileComponent,
    ],
    providers: [UserProfileGuard]
})

export class UserModule {
}
