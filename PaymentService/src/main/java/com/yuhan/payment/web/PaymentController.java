package com.yuhan.payment.web;

import com.yuhan.payment.entity.Payment;
import com.yuhan.payment.exception.ErrorResponse;
import com.yuhan.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author yuhan
 * @date 09.05.2021 - 15:51
 * @purpose
 */
@RestController
@RequestMapping("/payment")
@CrossOrigin
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @GetMapping("/findall")
    public List<Payment> findAll(){
        return paymentService.findAll();
    }

    @Operation(summary = "New payment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "New payment"),
            @ApiResponse(responseCode = "400", description = "Bad request format", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "payment not found", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PostMapping("/new")
    public Payment newPayment(@Valid @RequestBody Payment payment){
        return paymentService.paymentRequest(payment);
    }
    @Operation(summary = "Update payment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Update payment"),
            @ApiResponse(responseCode = "400", description = "Bad request format", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "payment not found", content = {@Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PostMapping("/update")
    public void update(@Valid @RequestBody Payment payment){
        paymentService.update(payment.getPaymentUid(), payment.getStatus());
    }
}
