import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { JDDSecretComponentComponent } from './jddsecret-component.component';

describe('JDDSecretComponentComponent', () => {
  let component: JDDSecretComponentComponent;
  let fixture: ComponentFixture<JDDSecretComponentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ JDDSecretComponentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(JDDSecretComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
