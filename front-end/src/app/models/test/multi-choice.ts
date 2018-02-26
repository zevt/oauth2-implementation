import {PersonalWord} from '../personal/personal-word';
import {TestMode} from './test-mode.enum';

export class MultiChoice {
    key: PersonalWord;
    options: PersonalWord[];
    testMode: TestMode;
    correctChoice: number;

}
