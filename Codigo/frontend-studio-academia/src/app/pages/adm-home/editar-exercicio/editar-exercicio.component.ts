import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ExerciseService } from 'src/app/services/ExerciseService';
import {User} from "../../../Models/user";
import {Exercicio} from "../../../Models/exercicio";

@Component({
  selector: 'app-editar-exercicio',
  templateUrl: './editar-exercicio.component.html',
  styleUrls: ['./editar-exercicio.component.css']
})
export class EditarExercicioComponent implements OnInit {

  editExercicioForm !: FormGroup
  idExercise !: String
  content !: any
  exerc!: any

  constructor(
    private exerciseService : ExerciseService,
    private formBuilder : FormBuilder,
    private routeAc : ActivatedRoute) {
      this.routeAc.params.subscribe(params => this.idExercise = params['idExercicio']);
      this.ngBuiltExercise( this.idExercise);
    }

  ngOnInit(): void {
    this.editExercicioForm = this.formBuilder.group({
      name : ['',Validators.required,],
      url : ['',Validators.required],
      description : ['',Validators.required]
    })
  }

  ngBuiltExercise( id : any) {

    console.log('search Exercise by id')
    this.exerciseService.findExercise(id)
      .subscribe(
        {
          next: (res) =>{
            this.content = res;
            }, error: (err) => {
            console.log(err)
          },complete: () =>{
            this.completeFields();
          }
        });
  }

  editar() {
    let body = {
      description : this.editExercicioForm.value["description"],
      exerciseUrl : this.editExercicioForm.value["url"],
      name : this.editExercicioForm.value["name"],
    }

    this.exerciseService.update(this.idExercise,body)
      .subscribe(
        {
          next:(res) => {
            console.log(res)
            alert("Exercício alterado com sucesso!")
          },
          error: (err) => {
            console.log(err)
            alert("Não foi pssível editar exercício!")
          }
        }
      );

  }

  private completeFields() {
    this.editExercicioForm = this.formBuilder.group({
      name : [this.content?.name,Validators.required,],
      url : [this.content?.exerciseUrl,Validators.required],
      description : [this.content?.description,Validators.required]
    })
  }
}
