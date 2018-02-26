import {Injectable} from '@angular/core';

declare let toastr: any;

@Injectable()
export class ToastrService {

    constructor() {
        // toastr.options.positionClass = 'toast-top-center';
    }

    success(message: string, title?: string) {

        toastr.options.positionClass = 'toast-top-right';
        toastr.success(message, title);
    }

    info(message: string, title?: string) {
        toastr.options.positionClass = 'toast-top-right';
        toastr.info(message, title);
    }

    warning(message: string, title?: string) {
        toastr.options.positionClass = 'toast-top-center';
        toastr.warning(message, title);
    }

    error(message: string, title?: string) {
        toastr.options.positionClass = 'toast-top-right';
        toastr.error(message, title);
    }
}

// export interface Toastr {
//     success(msg: string, title?: string): void;
//     success(msg: string, s, title?: string): void;
//     info(msg: string, title?: string): void;
//     warning(msg: string, title?: string): void;
//     error(msg: string, title?: string): void;
// }



