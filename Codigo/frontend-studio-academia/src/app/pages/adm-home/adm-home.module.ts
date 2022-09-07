import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdmHomeRoutingModule } from './adm-home-routing.module';
import { CadastroUsuarioComponent } from './cadastro-usuario/cadastro-usuario.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AdmHomeComponent } from './adm-home.component';



@NgModule({
  declarations: [
    AdmHomeComponent,
    CadastroUsuarioComponent
  ],
  imports: [
    CommonModule,
    AdmHomeRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ],
  exports:[
    AdmHomeComponent
  ]
})
export class AdmHomeModule { }
