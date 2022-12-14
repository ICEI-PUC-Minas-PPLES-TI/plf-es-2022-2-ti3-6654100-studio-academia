package com.example.empresasjava.controller;


import com.example.empresasjava.models.Exercise;
import com.example.empresasjava.models.RequestEntity.ExerciseRequest;

import com.example.empresasjava.models.dto.ExerciseDto;
import com.example.empresasjava.service.ExerciseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/exercise")
public class ExerciseController {

    @Autowired
    private ExerciseService exerciseService;

    @PostMapping(path = "/create")
    @ApiOperation(value = "Criar novo exercicio")
    @PreAuthorize("@authorityChecker.isAllowed({'ADMIN','PROFESSOR'})")
    public ResponseEntity<ExerciseDto> createExercise(
            @ApiParam(value = "Json da requisição que contem o dado do exercicio a ser salvo")
            @Valid @RequestBody ExerciseRequest request) throws NotFoundException {

        ExerciseDto exerciseDto = this.exerciseService.create(request);
        return ResponseEntity.ok().body(
                exerciseDto
        );
    }

    @PostMapping(path = "/edit/{id}")
    @ApiOperation(value = "editar  exercicio")
    @PreAuthorize("@authorityChecker.isAllowed({'ADMIN','PROFESSOR'})")
    public ResponseEntity<ExerciseDto> editExercise(
            @ApiParam(value = "Json da requisição que contem o dado do exercicio a ser salvo")
            @Valid @RequestBody ExerciseRequest request,
            @PathVariable Long id) throws NotFoundException {

        ExerciseDto exerciseDto = this.exerciseService.editExercise(request,id);

        return ResponseEntity.ok().body(
                exerciseDto
        );
    }

    @DeleteMapping(path = "/delete/id/{id}")
    @ApiOperation(value = "deletar o e exercicio")
    @PreAuthorize("@authorityChecker.isAllowed({'ADMIN','PROFESSOR'})")
    public ResponseEntity<ExerciseDto> deleteExercise(
            @ApiParam(value = "Json da requisição que contem o dado do exercicio a ser salvo")
            @PathVariable(value="id") final Long id) throws NotFoundException {

        ExerciseDto exerciseDto = this.exerciseService.deleteExercise(id);

        return ResponseEntity.ok().body(
                exerciseDto
        );
    }

    @PreAuthorize("@authorityChecker.isAllowed({'ADMIN','PROFESSOR'})")
    @GetMapping(path = "/page/{page}/size/{size}")
    @ResponseBody
    @ApiOperation(value = "Lista usuários por página quantidade")
    public Page<Exercise> listExercisesByPageWithSize(
            @ApiParam(value = "Página que deseja visualizar iniciando em 0", example = "0")
            @PathVariable(value="page")
            int page,
            @ApiParam(value = "Quantidade de usuários a serem listados por página", example = "10")
            @PathVariable(value="size")
            int size){

        Pageable pages = PageRequest.of(page, size);
        return this.exerciseService.listExercisesByPage(pages);

    }

    @PreAuthorize("@authorityChecker.isAllowed({'ADMIN','PROFESSOR'})")
    @GetMapping(path = "page/{page}/size/{size}/name/{name}")
    @ResponseBody
    @ApiOperation(value = "Lista usuários por página quantidade")
    public Page<Exercise> listSpecificUsersByPageWithSize(
            @ApiParam(value = "Página que deseja visualizar iniciando em 0", example = "0")
            @PathVariable(value="page")
            int page,
            @ApiParam(value = "Quantidade de usuários a serem listados por página", example = "10")
            @PathVariable(value="size")
            int size,
            @PathVariable(value="name")
            String name
    ){

        Pageable pages = PageRequest.of(page, size);

        return this.exerciseService.listSpecificUsersByPage(pages, name);

    }


    @PreAuthorize("@authorityChecker.isAllowed({'ADMIN','PROFESSOR'})")
    @GetMapping(path = "getexercisebyid/exerciseId/{exerciseId}")
    @ResponseBody
    @ApiOperation(value = "Lista usuários por página quantidade")
    public ResponseEntity<Exercise> getExerciseById(
            @PathVariable(value="exerciseId")
            Long exerciseId)throws NotFoundException{

        return ResponseEntity.ok().body(
                this.exerciseService.getExerciseById(exerciseId)
        );

    }

    @PreAuthorize("@authorityChecker.isAllowed({'ADMIN','PROFESSOR'})")
    @GetMapping(path = "/getexercisesdropdown")
    @ResponseBody
    @ApiOperation(value = "Lista usuários por página quantidade")
    public ResponseEntity<List<Exercise>> getExercisesForDropDown()
            throws NotFoundException{

        return ResponseEntity.ok().body(
                this.exerciseService.getExercisesForDropDown()
        );

    }



}
