package com.sumyk.payments.controller;

import com.sumyk.payments.model.Administrator;
import com.sumyk.payments.service.AdministratorService;
import com.sumyk.payments.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admins")
public class AdministratorController {

    private final AdministratorService service;

    @Autowired
    public AdministratorController(AdministratorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Administrator admin) {
        service.create(admin);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Administrator>> read() {
        final List<Administrator> admins = service.readAll();

        return admins != null &&  !admins.isEmpty()
                ? new ResponseEntity<>(admins, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Administrator> read(@PathVariable(name = "id") int id) {
        final Administrator admin = service.read(id);

        return admin != null
                ? new ResponseEntity<>(admin, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") int id, @RequestBody Administrator admin) {
        final boolean updated = service.update(admin, id);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        final boolean deleted = service.delete(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
    @PutMapping(value = "/{id}/accounts/{accountId}")
    public ResponseEntity<?> unblock(@PathVariable(name = "accountId") int accountId) {
        BankAccountService accountService = new BankAccountService();

        final boolean updated = accountService.unblock(accountId);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
