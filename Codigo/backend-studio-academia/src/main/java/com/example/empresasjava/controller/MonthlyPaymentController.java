package com.example.empresasjava.controller;

import com.example.empresasjava.models.MonthlyPayment;
import com.example.empresasjava.models.RequestEntity.MonthlyPaymentRequest;
import com.example.empresasjava.models.ResponseEntity.MonthlyPaymentResponse;
import com.example.empresasjava.service.MonthlyPaymentService;
import com.example.empresasjava.service.impl.MonthlyPaymentServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.imageio.IIOException;
import javax.validation.Valid;
import java.io.IOException;

    /*TODO
       TEST IMPLEMENTATIONS
    */


@RestController
@CrossOrigin
@RequestMapping("/monthly-payment")
public class MonthlyPaymentController {

    @Autowired
    private MonthlyPaymentService monthlyPaymentService;

    //usuario adicionar informações de pagamento
    @PostMapping(path = "/createRequestForApprove")
    @ApiOperation(value = "Criar nova requisição de mensalidade para aprovar")
    @PreAuthorize("@authorityChecker.isAllowed({'ALUNO','ADMIN'})")
    public ResponseEntity<MonthlyPaymentResponse> createUserMonthlyRequest(
            @ApiParam(value = "Json da requisição de pagamento do dado do pagamento mensal ")
            @RequestBody MonthlyPaymentRequest request) throws NotFoundException, IOException {


            MonthlyPaymentResponse monthlyPaymentResponse = this.monthlyPaymentService.createRequestForApprove(request);

        return ResponseEntity.ok().body(
                monthlyPaymentResponse
        );
    }

    @PostMapping(path = "/uploadImage")
    @ApiOperation(value = "Criar nova requisição de mensalidade para aprovar")
    @PreAuthorize("@authorityChecker.isAllowed({'ALUNO','ADMIN'})")
    public ResponseEntity<String> uploadImage(
            @ApiParam(value = "Json da requisição de pagamento do dado do pagamento mensal ")
            @RequestParam MultipartFile paymentVoucherImage ) throws NotFoundException, IOException {


        String savedPath = this.monthlyPaymentService.uploadImage(paymentVoucherImage );

        return ResponseEntity.ok().body(
                savedPath
        );
    }




    //se for usuario tem que verificar se a pessoa que ta tentanto deletar, tem o id
    //igual do parametro que ela passou, para evitar injecton

    @DeleteMapping(path = "/delete/{idUser}/")
    @ApiOperation(value = "deletar A REQUISIÇÃO ")
    //@PreAuthorize("@authorityChecker.isAllowed({'ADMIN'})")
    public ResponseEntity<MonthlyPaymentResponse> deleteMonthlyRequest(
            @ApiParam(value = "Json da requisição que contem o dado do exercicio a ser salvo")
            @Valid @RequestBody MonthlyPaymentRequest request,
            @PathVariable Long id) throws NotFoundException {

        MonthlyPaymentResponse monthlyPaymentResponse = this.monthlyPaymentService.deleteMonthlyRequest(request,id);

        return ResponseEntity.ok().body(
                monthlyPaymentResponse
        );
    }

    @PostMapping(path = "/editMonthlyPaymentRequest/{idUser}")
    @ApiOperation(value = "edita a requisição de mensalidade")
    @PreAuthorize("@authorityChecker.isAllowed({'ALUNO'})")
    public ResponseEntity<MonthlyPaymentResponse> editMonthlyPaymentRequest(
            @ApiParam(value = "Json da requisição que contem o dado do usuario a ser salvo")
            @Valid @RequestBody MonthlyPaymentRequest request,
            @PathVariable Long idUser) throws NotFoundException {

        MonthlyPaymentResponse monthlyPaymentResponse= this.monthlyPaymentService.editMonthlyPaymentRequest(request,idUser);

        return ResponseEntity.ok().body(monthlyPaymentResponse);

    }

    @GetMapping(path = "/pageAll/{page}/size/{size}")
    @ResponseBody
    @ApiOperation(value = "Lista todas as requisições (aprovadas ou nao)")
    @PreAuthorize("@authorityChecker.isAllowed({'ADMIN'})")
    public Page<MonthlyPayment> listRequestsByPageWithSize(
            @ApiParam(value = "Página que deseja visualizar iniciando em 0", example = "0")
            @PathVariable(value="page")
            int page,
            @ApiParam(value = "Quantidade de usuários a serem listados por página", example = "10")
            @PathVariable(value="size")
            int size)throws NotFoundException {

        Pageable pages = PageRequest.of(page, size);

        return this.monthlyPaymentService.listRequestsByPage(pages);

    }
    @GetMapping(path = "/pageAllPendency/{page}/size/{size}/paymentStatusRequest/{paymentStatusRequest}")
    @ResponseBody
    @ApiOperation(value = "lista todos pendente de aprovação")
    @PreAuthorize("@authorityChecker.isAllowed({'ADMIN'})")
    public Page<MonthlyPayment> listSpecificRequestsByPageWithSize(
            @ApiParam(value = "Página que deseja visualizar iniciando em 0", example = "0")
            @PathVariable(value="page")
            int page,
            @ApiParam(value = "Quantidade de usuários a serem listados por página", example = "10")
            @PathVariable(value="size")
            int size,
            @ApiParam(value = "Quantidade de usuários a serem listados por página", example = "10")
            @PathVariable(value="paymentStatusRequest")
            String paymentStatusRequest)throws NotFoundException {

        Pageable pages = PageRequest.of(page, size);

        return this.monthlyPaymentService.listSpecificRequestsByPage(pages,paymentStatusRequest);

    }


    @GetMapping(path = "/pageAll/{page}/size/{size}/{idUser}")
    @ResponseBody
    @ApiOperation(value = "Lista pagamentos do usuario (aprovado ou nao)")
    @PreAuthorize("@authorityChecker.isAllowed({'ADMIN'})")
    public Page<MonthlyPayment> listUserRequestsByPageWithSize(
            @ApiParam(value = "Página que deseja visualizar iniciando em 0", example = "0")
            @PathVariable(value="page")
            int page,
            @ApiParam(value = "Quantidade de usuários a serem listados por página", example = "10")
            @PathVariable(value="size")
            int size,
            @ApiParam(value = "Quantidade de usuários a serem listados por página", example = "10")
            @PathVariable(value="idUser")
            String idUser)throws NotFoundException {

            Long id = Long.parseLong(idUser);
            Pageable pages = PageRequest.of(page, size);

            return this.monthlyPaymentService.listUserRequestsByPage(pages,id);

    }
    @GetMapping(path = "/pageAllPendency/{page}/size/{size}/{idUser}/paymentStatusRequest/{paymentStatusRequest}")
    @ResponseBody
    @ApiOperation(value = "lista apenas as requisições pendentes")
    @PreAuthorize("@authorityChecker.isAllowed({'ADMIN'})")
    public Page<MonthlyPayment> listUserSpecificRequestsByPageWithSize(
            @ApiParam(value = "Página que deseja visualizar iniciando em 0", example = "0")
            @PathVariable(value="page")
            int page,
            @ApiParam(value = "Quantidade de usuários a serem listados por página", example = "10")
            @PathVariable(value="size")
            int size,
            @ApiParam(value = "Quantidade de usuários a serem listados por página", example = "10")
            @PathVariable(value="idUser")
            String idUser,
            @PathVariable(value="paymentStatusRequest")
            String paymentStatusRequest)throws NotFoundException {

            Long id = Long.parseLong(idUser);
            Pageable pages = PageRequest.of(page, size);

            return this.monthlyPaymentService.listUserSpecificRequestsByPage(pages,id,paymentStatusRequest);

    }



    @PostMapping(path = "/aproveRequest/{idMonthlyRequest}")
    @ApiOperation(value = "aprova uma requisição de mensalidade pendente")
    @PreAuthorize("@authorityChecker.isAllowed({'ADMIN'})")
    public ResponseEntity<MonthlyPaymentResponse> approveMonthlyRequest(
            @ApiParam(value = "Json da requisição que contem o dado do exercicio a ser salvo")
            @Valid @RequestBody MonthlyPaymentRequest request,
            @ApiParam(value = "Quantidade de usuários a serem listados por página", example = "10")
            @PathVariable(value="idMonthlyRequest")
            String idMonthlyRequest) throws NotFoundException {

            Long id = Long.parseLong(idMonthlyRequest);
            MonthlyPaymentResponse monthlyPaymentResponse = this.monthlyPaymentService.approveMonthlyRequest(request,id);

            return ResponseEntity.ok().body(
                    monthlyPaymentResponse
            );
    }

    @PostMapping(path = "/reproveRequest/{idMonthlyRequest}")
    @ApiOperation(value = "aprova uma requisição de mensalidade pendente")
    @PreAuthorize("@authorityChecker.isAllowed({'ADMIN'})")
    public ResponseEntity<MonthlyPaymentResponse> reproveMonthlyRequest(
            @ApiParam(value = "Json da requisição que contem o dado do exercicio a ser salvo")
            @Valid @RequestBody MonthlyPaymentRequest request,
            @ApiParam(value = "Quantidade de usuários a serem listados por página", example = "10")
            @PathVariable(value="idMonthlyRequest")
            String idMonthlyRequest) throws NotFoundException {

        Long id = Long.parseLong(idMonthlyRequest);
        MonthlyPaymentResponse monthlyPaymentResponse = this.monthlyPaymentService.reproveMonthlyRequest(request,id);

        return ResponseEntity.ok().body(
                monthlyPaymentResponse
        );
    }



}
