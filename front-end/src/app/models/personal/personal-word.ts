import {Sense} from './sense';
import {DisplayMode} from "../display-mode.enum";

export class PersonalWord {
    entity: string;
    senses: Sense[];
    categories: string[];
    imageUrls: string[];
    displayMode: DisplayMode;

    // constructor(pw: PersonalWord) {
    //     this.entity = pw.entity;
    //     this.senses = pw.senses;
    //     this.categories = pw.categories;
    //     this.imageUrls = pw.imageUrls;
    //     this.displayMode = pw.displayMode;
    // }
}
