import { NgModule, Component } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdmHomeComponent } from './adm-home.component';
import { CadastroAlunoComponent } from './cadastro-aluno/cadastro-aluno.component';
import { CadastroUsuarioComponent } from './cadastro-usuario/cadastro-usuario.component';
import {AuthGuard} from "../../shared/guards/AuthGuard";
import { CadastroExercicioComponent } from './cadastro-exercicio/cadastro-exercicio.component';
import { SearchHomeComponent } from './search-home/search-home.component';
import { CadastroFichaComponent } from './cadastro-ficha/cadastro-ficha.component';
import { EditarAlunoComponent } from './editar-aluno/editar-aluno.component';
import { AdmGradeFichasComponent } from './adm-grade-fichas/adm-grade-fichas.component';
import { EditarFichasComponent } from './editar-fichas/editar-fichas.component';


const routes: Routes = [
  {
    path: '',
    component: AdmHomeComponent,
    children:[
      {
        path: '',
        component: SearchHomeComponent
      },
      {
        path: 'cadastrarUsuario',
        component: CadastroUsuarioComponent,
        canActivate: [AuthGuard]
      },
      {
        path: 'cadastrarAluno',
        component: CadastroAlunoComponent,
        canActivate: [AuthGuard]
      },
      {
        path: 'cadastrarExercicio',
        component: CadastroExercicioComponent
      },
      {
        path: 'editarAluno/:idAluno',
        component: EditarAlunoComponent
      },
      {
        path: 'gradeFichas/:idAluno',
        component: AdmGradeFichasComponent
      },
      {
        path: 'editarFichas/:idFicha',
        component: EditarFichasComponent
      },
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdmHomeRoutingModule { }
