import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ToeicComponent } from './toeic.component';

describe('ToeicComponent', () => {
  let component: ToeicComponent;
  let fixture: ComponentFixture<ToeicComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ToeicComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ToeicComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
