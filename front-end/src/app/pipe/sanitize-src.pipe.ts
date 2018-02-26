import {Pipe, PipeTransform} from '@angular/core';
import {DomSanitizer, SafeUrl} from "@angular/platform-browser";

@Pipe({
    name: 'sanitizeSrc'
})
export class SanitizeSrcPipe implements PipeTransform {

    constructor(private sanitize: DomSanitizer) {
    }

    transform(value: any, args?: any): SafeUrl {
        return this.sanitize.bypassSecurityTrustResourceUrl(value);
    }

}
