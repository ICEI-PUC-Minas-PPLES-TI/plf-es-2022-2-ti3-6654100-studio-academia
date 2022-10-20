package com.example.empresasjava.models.ResponseEntity;


import com.example.empresasjava.models.MonthlyPayment;
import com.example.empresasjava.models.Plans;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class PlansResponse {

    private Long planId;

    private String planCode;

    private String name ;

    private String contractedDays;

    private BigDecimal price;

    private String description;


    public PlansResponse(Long planId, String planCode, String name, String contractedDays, BigDecimal price, String description) {
        this.planId = planId;
        this.planCode = planCode;
        this.name = name;
        this.contractedDays = contractedDays;
        this.price = price;
        this.description = description;
    }



    public static PlansResponse fromPlans (Plans plan){

        return new PlansResponse(
                plan.getPlanId(),
                plan.getPlanCode(),
                plan.getName(),
                plan.getContractedDays(),
                plan.getPrice(),
                plan.getDescription()

        );
    }
}
