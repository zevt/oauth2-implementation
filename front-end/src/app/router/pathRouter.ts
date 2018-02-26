import {Routes} from "@angular/router";
import {ToeicComponent} from "../components/path/toeic/toeic.component";
import {PathComponent} from "../components/path/path/path.component";
import {AuthGuard} from "../guards/auth.guard";

export const PathRoute: Routes = [
    {path: '', component: PathComponent, canActivate: [AuthGuard]},
    {path: 'toeic', component: ToeicComponent}
]
