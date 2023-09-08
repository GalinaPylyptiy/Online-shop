package com.epam.electronic.service;

import com.epam.electronic.config.PayPalClientConfig;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PayPalService {

//    private final APIContext apiContext;

//    public PayPalService(APIContext apiContext) {
//        this.apiContext = apiContext;
//    }
    private final PayPalClientConfig config;

    public PayPalService(PayPalClientConfig config) {
        this.config = config;
    }

    public Payment createPayment(double totalAmount, String currency, String paymentMethod, String cancelUrl, String successUrl) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setIntent("sale");

        Payer payer = new Payer();
        payer.setPaymentMethod(paymentMethod);
        payment.setPayer(payer);

        Transaction transaction = new Transaction();
        Amount amount = new Amount();
        amount.setCurrency(currency);
        Integer intAmount = (int) totalAmount;
        amount.setTotal(String.valueOf(intAmount));
        transaction.setAmount(amount);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        payment.setTransactions(transactions);
        return payment.create(config.apiContext());
    }

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);
        return payment.execute(config.apiContext(), paymentExecution);
    }
}

