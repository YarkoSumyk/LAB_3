package com.sumyk.payments.service;

import com.sumyk.payments.model.Payment;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@org.springframework.stereotype.Service
public class PaymentService {
    private static final Map<Integer, Payment> PAYMENT_REPOSITORY_MAP = new HashMap<>();

    private static final AtomicInteger PAYMENT_ID_HOLDER = new AtomicInteger();
    public void create(Payment payment, int accountId) {
        final int paymentId = PAYMENT_ID_HOLDER.incrementAndGet();
        payment.setPaymentId(paymentId);
        payment.setSenderAccountId(accountId);
        Date dateTime = new Date();
        payment.setDateTime(dateTime);
        PAYMENT_REPOSITORY_MAP.put(paymentId, payment);

    }
    public List<Payment> readByAccountId(int accountId) {
        List<Payment> payments = new ArrayList<>(PAYMENT_REPOSITORY_MAP.values());
        List<Payment> paymentsById = new ArrayList<Payment>();
        payments.forEach(payment -> {
            if(payment.getSenderAccountId()==accountId){
                paymentsById.add(payment);
            }
        });
        return paymentsById;
    }

    public Payment read(int paymentId) {
        return PAYMENT_REPOSITORY_MAP.get(paymentId);
    }

    public boolean update(Payment payment, int paymentId) {
        if (PAYMENT_REPOSITORY_MAP.containsKey(paymentId)) {
            payment.setPaymentId(paymentId);
            PAYMENT_REPOSITORY_MAP.put(paymentId, payment);
            return true;
        }

        return false;
    }

    public boolean delete(int paymentId) {
        return PAYMENT_REPOSITORY_MAP.remove(paymentId) != null;
    }
}
