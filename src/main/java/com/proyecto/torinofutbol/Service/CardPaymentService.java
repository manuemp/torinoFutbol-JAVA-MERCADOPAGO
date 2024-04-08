package com.proyecto.torinofutbol.Service;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.customer.CustomerCardClient;
import com.mercadopago.client.customer.CustomerCardCreateRequest;
import com.mercadopago.client.customer.CustomerClient;
import com.mercadopago.client.customer.CustomerRequest;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.customer.Customer;
import com.mercadopago.resources.customer.CustomerCardIssuer;
import com.mercadopago.resources.payment.Payment;
import com.proyecto.torinofutbol.dto.CardPaymentDTO;
import com.proyecto.torinofutbol.dto.PaymentResponseDTO;
import com.proyecto.torinofutbol.exception.MercadoPagoException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CardPaymentService {
//    @Value("${mercado_pago_sample_access_token}")
//    @Value("TEST-2454211397602545-012810-a231c9e4443c6f687409dadcd4abd8f5-115251485")
    @Value("APP_USR-2454211397602545-012810-f4969b3234161813c5c29e657c0ccfac-115251485")
    //VALUE TORINOFUTBOLAPP
//    @Value("APP_USR-4091602927557442-012908-7f27999d2c3ce25437c7ad829c0aba7f-1656286779")
//    @Value("TEST-4091602927557442-012908-06ba9d0cf4ca639bce34cbde4f4ba241-1656286779")
    private String mercadoPagoAccessToken;

    public PaymentResponseDTO processPayment(CardPaymentDTO cardPaymentDTO) {
        try {
            MercadoPagoConfig.setAccessToken(mercadoPagoAccessToken);

            PaymentClient paymentClient = new PaymentClient();

//            System.out.println("\n\nTOKEN: " + cardPaymentDTO.getToken());
//            System.out.println("TIPO: " + cardPaymentDTO.getPayer().getIdentification().getType());
//            System.out.println("ID: " + cardPaymentDTO.getPayer().getIdentification().getNumber());
//            System.out.println(cardPaymentDTO.getPayer().getEmail() + "\n\n\n");

            PaymentCreateRequest paymentCreateRequest =
                    PaymentCreateRequest.builder()
                            .transactionAmount(BigDecimal.valueOf(40))
                            .token(cardPaymentDTO.getToken())
                            .description(cardPaymentDTO.getProductDescription())
                            .installments(cardPaymentDTO.getInstallments())
                            .paymentMethodId(cardPaymentDTO.getPaymentMethodId())
                            .payer(
                                    PaymentPayerRequest.builder()
                                            .email(cardPaymentDTO.getPayer().getEmail())
                                            .identification(
                                                    IdentificationRequest.builder()
                                                            .type(cardPaymentDTO.getPayer().getIdentification().getType())
                                                            .number(cardPaymentDTO.getPayer().getIdentification().getNumber())
                                                            .build())
                                            .build())
                            .build();

            Payment createdPayment = paymentClient.create(paymentCreateRequest);

            System.out.println("\n\n\nCAPTURED = " + createdPayment.isCaptured());
            System.out.println("STATUS = " + createdPayment.getStatus());
            System.out.println("AUTH CODE = " + createdPayment.getAuthorizationCode());
            System.out.println("TRANSACTION AMOUNT = " + createdPayment.getTransactionAmount());
            System.out.println("PAYMENT ID = " + createdPayment.getId() + "\n\n\n");

//            MercadoPagoConfig.setAccessToken("TEST-2454211397602545-012810-a231c9e4443c6f687409dadcd4abd8f5-115251485");
//            Long paymentId = 123456789L;

            return new PaymentResponseDTO(
                    createdPayment.getId(),
                    String.valueOf(createdPayment.getStatus()),
                    createdPayment.getStatusDetail());
        } catch (MPApiException apiException) {
            System.out.println(apiException.getApiResponse().getContent());
            throw new MercadoPagoException(apiException.getApiResponse().getContent());
        } catch (MPException exception) {
            System.out.println(exception.getMessage());
            throw new MercadoPagoException(exception.getMessage());
        }
    }
}
