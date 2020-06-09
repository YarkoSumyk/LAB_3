package com.sumyk.payments.controller;

import com.sumyk.payments.model.BankAccount;
import com.sumyk.payments.model.Payment;
import com.sumyk.payments.service.BankAccountService;
import com.sumyk.payments.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("clients/{clientId}/accounts/{accountId}/payments")
public class PaymentController {

    private final PaymentService service;
    @Autowired
    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> create(@PathVariable(name = "accountId") int accountId, @RequestBody Payment payment) {
        service.create(payment, accountId);
        BankAccountService accountService = new BankAccountService();
        if(accountService.debiting(payment)){
            accountService.refill(payment);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping
    public ResponseEntity<List<Payment>> readAllPayments(@PathVariable(name = "accountId") int accountId) {
        final List<Payment> payments = service.readByAccountId(accountId);

        return payments != null &&  !payments.isEmpty()
                ? new ResponseEntity<>(payments, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Payment> read(@PathVariable(name = "id") int paymentId) {
        final Payment payment = service.read(paymentId);

        return payment != null
                ? new ResponseEntity<>(payment, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") int paymentId, @RequestBody Payment payment) {
        final boolean updated = service.update(payment, paymentId);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int paymentId) {
        final boolean deleted = service.delete(paymentId);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
