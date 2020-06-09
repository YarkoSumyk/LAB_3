package com.sumyk.payments.controller;

import com.sumyk.payments.model.BankAccount;
import com.sumyk.payments.model.Payment;
import com.sumyk.payments.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("clients/{clientId}/accounts")
public class BankAccountController {

    private final BankAccountService service;

    @Autowired
    public BankAccountController(BankAccountService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> create(@PathVariable(name = "clientId") int clientId, @RequestBody BankAccount account) {
        service.create(account, clientId);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BankAccount>> read(@PathVariable(name = "clientId") int clientId) {
        final List<BankAccount> accounts = service.readByClientId(clientId);

        return accounts != null &&  !accounts.isEmpty()
                ? new ResponseEntity<>(accounts, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping(value = "/{accountId}")
    public ResponseEntity<BankAccount> read(@PathVariable(name = "clientId") int clientId,@PathVariable(name = "accountId") int accountId) {
        final BankAccount account = service.read(accountId);

        return account != null
                ? new ResponseEntity<>(account, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/{accountId}")
    public ResponseEntity<?> update(@PathVariable(name = "accountId") int accountID, @RequestBody BankAccount account) {
        final boolean updated = service.update(account, accountID);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping(value = "/{accountId}/block")
    public ResponseEntity<?> block(@PathVariable(name = "accountId") int accountId) {
        final boolean updated = service.block(accountId);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/{accountId}")
    public ResponseEntity<?> delete(@PathVariable(name = "accountId") int accountID) {
        final boolean deleted = service.delete(accountID);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
