import {TestMode} from './test-mode.enum';
import {Answer} from './answer.enum';

export class TestResult {
    entity: string;
    private testMode: TestMode;
    private answer: Answer;

    constructor() {
    }

    setEntity(value: string): TestResult {
        this.entity = value;
        return this;
    }

    setTestMode(value: TestMode): TestResult {
        this.testMode = value;
        return this;
    }

    setAnswer(value: Answer): TestResult {
        this.answer = value;
        return this;
    }

    isValid() {
        return this.entity != null && this.answer != null && this.testMode != null;
    }
}
