package com.proyecto.torinofutbol.Controllers;

import javax.validation.Valid;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.proyecto.torinofutbol.Service.CardPaymentService;
import com.proyecto.torinofutbol.dto.CardPaymentDTO;
import com.proyecto.torinofutbol.dto.PaymentResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/process_payment", method = {
        RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE
})
public class CardPaymentController {
    private final CardPaymentService cardPaymentService;

    @Autowired
    public CardPaymentController(CardPaymentService cardPaymentService) {
        this.cardPaymentService = cardPaymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponseDTO> processPayment(@RequestBody @Valid CardPaymentDTO cardPaymentDTO){
        PaymentResponseDTO payment = cardPaymentService.processPayment(cardPaymentDTO);
        System.out.println("\n\n\nSTATUS FINAL: " + payment.getStatus());
        System.out.println("DETAIL: " + payment.getDetail());
        System.out.println("ID: " + payment.getId() + "\n\n\n");
        return ResponseEntity.status(HttpStatus.CREATED).body(payment);
    }

}

