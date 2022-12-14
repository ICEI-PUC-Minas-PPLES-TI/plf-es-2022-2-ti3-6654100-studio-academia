import { Observable } from 'rxjs';
import { Component,Input, OnInit } from '@angular/core';
import { Ficha, Fichas } from 'src/app/Models/ficha';
import { pageableObject } from 'src/app/Models/PageableObject';
import { AuthService } from 'src/app/services/AuthService';
import { UserFileService } from 'src/app/services/UserFileService';


@Component({
  selector: 'app-grade-fichas',
  templateUrl: './grade-fichas.component.html',
  styleUrls: ['./grade-fichas.component.css']
})
export class GradeFichasComponent implements OnInit {

 fichas$ !: Fichas
 pageable !: pageableObject
 actualUser !: any

  constructor(
    private authService : AuthService,
    private userFileService : UserFileService
  ) { }

  ngOnInit(): void {

    this.actualUser = this.authService.getSession().user.id

    this.listarFichas();
  }

  listarFichas(){

    this.userFileService.listarFichasUser(0,10,this.actualUser).subscribe({
      next:(res)=>{
        // console.log('res->'+res),
        this.pageable = res,
        this.fichas$ = <Fichas>this.pageable?.content
        // console.log('fichas$$$$$$$->'+this.fichas$)

      },error:(err)=>{
        console.log(err)
      }
    })

  }
}
