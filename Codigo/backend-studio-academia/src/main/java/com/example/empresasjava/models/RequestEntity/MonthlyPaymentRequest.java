package com.example.empresasjava.models.RequestEntity;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class MonthlyPaymentRequest {


    @NotNull(message = "Campo email não pode ser nulo")
    private Date dueDate;

    @NotNull(message = "Campo email não pode ser nulo")
    private Long userId;

    private String paymentVoucher;

    private String message;

    private Long monthlyPaymentId;
}



