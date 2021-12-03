import {Component, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.sass', '../styles.sass']
})
export class AdminPanelComponent implements OnInit {

  @Input()
  @Output()
  //todo change to false
  opened: boolean = true

  ngOnInit(): void {
  }

  openPopup() {
    this.opened = true
  }


}
