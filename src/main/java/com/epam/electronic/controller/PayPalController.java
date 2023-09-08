package com.epam.electronic.controller;

import com.epam.electronic.model.Order;
import com.epam.electronic.service.OrderService;
import com.epam.electronic.service.PayPalService;
import com.paypal.api.payments.Links;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/paypal")
public class PayPalController {

    private final PayPalService payPalService;
    private final OrderService orderService;
    private final Map<String, String> paypalSdkConfig;

    public PayPalController(PayPalService payPalService, OrderService orderService, Map<String, String> paypalSdkConfig) {
        this.payPalService = payPalService;
        this.orderService = orderService;
        this.paypalSdkConfig = paypalSdkConfig;
    }

    @PostMapping("/create-payment/{orderId}")
    public String payment(@PathVariable("orderId") Long orderId) {
        Order order = orderService.getOrderById(orderId);
        try {
            Payment payment = payPalService.createPayment(order.getTotalPayment(),"USD", "paypal",
                    paypalSdkConfig.get("cancelUrl"), paypalSdkConfig.get("successUrl"));
            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    return "redirect:"+link.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            throw new RuntimeException("Error creating payment: "+ e.getDetails());
        }
        return "redirect:/";
    }

    @GetMapping(value ="/cancel" )
    public ResponseEntity<String> cancelPay() {
        return ResponseEntity.ok("Canceled");
    }

    @GetMapping(value = "/success" )
    public ResponseEntity<String> successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = payPalService.executePayment(paymentId, payerId);
            System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
                return ResponseEntity.ok("Success");
            }
        } catch (PayPalRESTException e) {
            throw new RuntimeException("Error creating payment: "+ e.getDetails());
        }
        return ResponseEntity.ok("Failed");
    }
}