import {Routes} from "@angular/router";
import {HomeComponent} from "../components/home/home.component";
import {LoginComponent} from "../components/login/login.component";
import {AuthGuard} from "../guards/auth.guard";
import {AuthResolver} from "../resolver/AuthResolver";
import {JDDSecretComponentComponent} from '../components/jddsecret-component/jddsecret-component.component';

export const appRoute: Routes = [

    {path: 'login', component: LoginComponent,  resolve: {authResolver: AuthResolver}},
    // canActivate: [LoginGuard],
    {path: 'home', component: HomeComponent, resolve: {authResolver: AuthResolver}},
    {path: 'secret', component: JDDSecretComponentComponent, canActivate: [AuthGuard]},
    {path: '', component: HomeComponent, resolve: {authResolver: AuthResolver}},
    {path: '**', redirectTo: '/', pathMatch: 'full'},


];
